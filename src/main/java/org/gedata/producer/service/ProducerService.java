package org.gedata.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.gedata.producer.generator.DataProducer;
import org.gedata.producer.generator.SQLInsertProducer;
import org.gedata.producer.model.data.DownloadData;
import org.gedata.producer.model.producer.GenericData;
import org.gedata.producer.model.producer.InputProducer;
import org.gedata.producer.repository.IGenericDataRepository;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProducerService {

    Logger logger = LoggerFactory.logger(ProducerService.class);

    private final DataProducer dataProducer;
    private final IGenericDataRepository iGenericDataRepository;
    private final SQLInsertProducer sqlInsertProducer;

    public ProducerService(DataProducer dataProducer, IGenericDataRepository iGenericDataRepository,
                           SQLInsertProducer sqlInsertProducer) {
        this.dataProducer = dataProducer;
        this.iGenericDataRepository = iGenericDataRepository;
        this.sqlInsertProducer = sqlInsertProducer;
    }

    public GenericData saveData(GenericData genericData) throws JsonProcessingException {
        dataProducer.validateJsonString(genericData.getJsonModel());
        logger.info(String.format("Data to persist: %s",genericData.toString()));
        return iGenericDataRepository.save(genericData);
    }

    public JsonNode produceDataOnDemand(InputProducer inputProducer) throws JsonProcessingException {
        if(!(inputProducer.getOutputFormat().equals("json")||inputProducer.getOutputFormat().equals("sql"))){
            throw new IllegalArgumentException("");
        }
        logger.info(String.format("Produce generic data on direct call for template: %s , format: %s",
                inputProducer.getJsonModel(),inputProducer.getOutputFormat()));
        return inputProducer.getOutputFormat().equals("json") ? dataProducer.produceGenericData(inputProducer.getJsonModel()) :
                sqlInsertProducer.insertProducer(inputProducer.getJsonModel()) ;
    }

    public Optional<GenericData> getGenericDataById(Long id) {
        logger.info(String.format("Get generic data template for id: %d",id));
        return iGenericDataRepository.findById(id);
    }

    public DownloadData prepareDataForDownloadById(Long id) throws JsonProcessingException {
        logger.info(String.format("Prepare data for id: %d to download",id));
        GenericData data = getGenericDataById(id).orElse(null);
        if (!Objects.isNull(data)) {
            logger.debug(String.format("Prepared data for id: %d, data: %s",id,data));
            return new DownloadData(dataProducer.prepareDataForDownload(data.getJsonModel()), data.getDataName(), Instant.now());
        }
        return new DownloadData("No such a content available".getBytes(), "NoContent", Instant.now());
    }

    public Optional<GenericData> updateGenericData(Long id, GenericData newGenercData) {
        logger.info(String.format("GenericData to update for id: %d, content: %s",id,newGenercData));
        Optional<GenericData> genericData = iGenericDataRepository.findById(id);
        if (genericData.isPresent()) {
            genericData = genericData.map(g -> {
                g.setDataName(newGenercData.getDataName());
                g.setLastModified(Instant.now());
                g.setJsonModel(newGenercData.getJsonModel());
                g.setHostTarget(newGenercData.getHostTarget());
                return g;
            });
            iGenericDataRepository.save(genericData.get());
        }
        return genericData;
    }
}
