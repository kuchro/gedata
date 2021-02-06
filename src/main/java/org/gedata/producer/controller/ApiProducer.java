package org.gedata.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.gedata.producer.generator.DataPicker;
import org.gedata.producer.generator.GeneratorProvider;
import org.gedata.producer.model.data.DeletedData;
import org.gedata.producer.model.data.DownloadData;
import org.gedata.producer.model.dto.GenericDataDetailedDto;
import org.gedata.producer.model.dto.ReqGenericDataDto;
import org.gedata.producer.model.producer.GenericData;
import org.gedata.producer.model.producer.InputProducer;
import org.gedata.producer.service.ProducerService;
import org.gedata.producer.dtomapper.producer.DtoProducerMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("v1/producer")
public class ApiProducer {

    private ProducerService producerService;
    private DataPicker dataPicker;

    @PostMapping("/save")
    public ResponseEntity<ReqGenericDataDto> saveGenericData(@Valid @RequestBody ReqGenericDataDto reqGenericDataDto) throws JsonProcessingException {
       return ResponseEntity.status(201)
               .body(DtoProducerMapper.convertToGenericDataDto(
                       producerService.saveData(reqGenericDataDto)));

    }

    @PostMapping("/generate")
    public ResponseEntity<JsonNode> produceData(@RequestBody InputProducer inputProducer) throws JsonProcessingException, FileNotFoundException {
        return ResponseEntity.ok(producerService.produceDataOnDemand(inputProducer));
    }

    @GetMapping
    public ResponseEntity<List<GenericDataDetailedDto>> getAllData(){
        return ResponseEntity.ok().body(producerService.getAllGenericData().stream()
                .map(DtoProducerMapper::convertToGDDetailedDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericDataDetailedDto> getGenericDataById(@PathVariable Long id) throws JsonProcessingException {
        return ResponseEntity.ok(DtoProducerMapper.convertToGDDetailedDto(producerService.getGenericDataById(id)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericData> updateGenericData(@PathVariable Long id,@RequestBody ReqGenericDataDto reqGenericDataDto){
        return producerService.updateGenericData(id,DtoProducerMapper.convertToGenericData(reqGenericDataDto))
                .map(genericData -> ResponseEntity.status(204).body(genericData))
                .orElseThrow(()->new NoSuchElementException(String.format("Data with id: %s does not exist.",id)));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> downloadData(@PathVariable Long id) throws IOException {
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
        Map<String,List<String>> data = new HashMap<>();
        data.put("calledByName",Arrays.stream(GeneratorProvider.class.getDeclaredMethods())
                .map(Method::getName).collect(Collectors.toList()));
        data.put("personalDataGen('<nameFromList>')",dataPicker.perdonalData());
        return ResponseEntity.status(200)
                .body(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletedData> deleteDataById(@PathVariable Long id){
        producerService.deleteGenericData(id);
        return ResponseEntity.ok(new DeletedData(String.format("User with id %s successfuly deleted.",id)));
    }
}
