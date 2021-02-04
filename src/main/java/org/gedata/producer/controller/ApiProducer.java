package org.gedata.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.gedata.producer.generator.GeneratorProvider;
import org.gedata.producer.model.data.DownloadData;
import org.gedata.producer.model.dto.GenericDataDto;
import org.gedata.producer.model.producer.InputProducer;
import org.gedata.producer.service.ProducerService;
import org.gedata.producer.utils.DtoProducerMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@RestController
@RequestMapping("v1/producer")
public class ApiProducer {

    private ProducerService producerService;
    public ApiProducer(ProducerService producerService){
        this.producerService=producerService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveGenericData(@RequestBody GenericDataDto genericDataDto) throws JsonProcessingException {
       return ResponseEntity.status(201)
               .body(DtoProducerMapper.convertToGenericDataDto(
                       producerService.saveData(DtoProducerMapper.convertToGenericData(genericDataDto))));

    }

    @PostMapping("/generate")
    public ResponseEntity<?> produceData(@RequestBody InputProducer inputProducer) throws JsonProcessingException, FileNotFoundException {
        return ResponseEntity.ok(producerService.produceDataOnDemand(inputProducer));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getGenericDataById(@PathVariable Long id) throws JsonProcessingException {
        return producerService.getGenericDataById(id)
                .map(genericData -> ResponseEntity.ok().body(DtoProducerMapper.convertToGDDetailedDto(genericData)))
                .orElseThrow(()->new NoSuchElementException(String.format("Data with id: %s does not exist.",id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenericData(@PathVariable Long id,@RequestBody GenericDataDto genericDataDto){
        return producerService.updateGenericData(id,DtoProducerMapper.convertToGenericData(genericDataDto))
                .map(genericData -> ResponseEntity.status(204).body(genericData))
                .orElseThrow(()->new NoSuchElementException(String.format("Data with id: %s does not exist.",id)));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadData(@PathVariable Long id) throws JsonProcessingException {
        DownloadData downloadData = producerService.prepareDataForDownloadById(id);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(downloadData.getContent()));
        return ResponseEntity.ok()
                .header("Content-Disposition", String.format("attachment; filename=\"%s_%s.json\""
                        ,downloadData.getFileName(),downloadData.getDownloadTime()))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/generator")
    public ResponseEntity<?> listGenerators() {
        return ResponseEntity.status(200)
                .body(Arrays.stream(GeneratorProvider.class.getDeclaredMethods())
                        .map(Method::getName).collect(Collectors.toList()));
    }
}
