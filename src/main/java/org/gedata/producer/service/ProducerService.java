package org.gedata.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.gedata.producer.generator.DataProducer;
import org.gedata.producer.generator.SQLInsertProducer;
import org.gedata.producer.model.data.DownloadData;
import org.gedata.producer.model.producer.GenericData;
import org.gedata.producer.model.producer.InputProducer;
import org.gedata.producer.repository.GenericDataRepository;
import org.gedata.producer.utils.JsonModelValidator;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class ProducerService {

    private final Logger logger = LoggerFactory.logger(ProducerService.class);

    private final DataProducer dataProducer;
    private final GenericDataRepository genericDataRepository;
    private final SQLInsertProducer sqlInsertProducer;
    private final JsonModelValidator jsonModelValidator;


    public List<GenericData> getAllGenericData(){
        return genericDataRepository.findAll();
    }

    public GenericData saveData(GenericData genericData) throws JsonProcessingException {
        jsonModelValidator.validateJsonString(genericData.getJsonModel(),genericData.getOutputFormat());
        logger.info(String.format("Data to persist: %s",genericData.toString()));
        return genericDataRepository.save(genericData);
    }

    public JsonNode produceDataOnDemand(InputProducer inputProducer) throws JsonProcessingException {
        if(!(inputProducer.getOutputFormat().equals("json")||inputProducer.getOutputFormat().equals("sql"))){
            throw new IllegalArgumentException("");
        }
        jsonModelValidator.validateJsonString(inputProducer.getJsonModel(),inputProducer.getOutputFormat());
        logger.info(String.format("Produce generic data on direct call for template: %s , format: %s",
                inputProducer.getJsonModel(),inputProducer.getOutputFormat()));
        return inputProducer.getOutputFormat().equals("json") ? dataProducer.produceGenericData(inputProducer.getJsonModel()) :
                sqlInsertProducer.insertProducer(inputProducer.getJsonModel()) ;
    }

    public Optional<GenericData> getGenericDataById(Long id) {
        logger.info(String.format("Get generic data template for id: %d",id));
        return genericDataRepository.findById(id);
    }

    public DownloadData prepareDataForDownloadById(Long id) throws IOException {
        logger.info(String.format("Prepare data for id: %d to download",id));
        GenericData data = getGenericDataById(id)
                .orElseThrow(()-> new NoSuchElementException(String.format("There is not data for id: %s",id)));
        logger.debug(String.format("Prepared data for id: %d, data: %s",id,data));
        var genericData = produceDataOnDemand(new InputProducer(data.getOutputFormat(),data.getJsonModel()));
        return new DownloadData(dataProducer.prepareDataForDownload(genericData),
                data.getDataName(), Instant.now());
    }

    public Optional<GenericData> updateGenericData(Long id, GenericData newGenercData) {
        logger.info(String.format("GenericData to update for id: %d, content: %s",id,newGenercData));
        return Optional.ofNullable(genericDataRepository.findById(id)
                .map(g -> {
                    g.setDataName(newGenercData.getDataName());
                    g.setLastModified(Instant.now());
                    g.setJsonModel(newGenercData.getJsonModel());
                    g.setHostTarget(newGenercData.getHostTarget());
                    genericDataRepository.save(g);
                    return g;
                }).orElseThrow(() -> new NoSuchElementException(String.format("Data with id: %s does not exist.", id))));
    }

    public void deleteGenericData(Long id){
        genericDataRepository.deleteById(id);
    }
}
