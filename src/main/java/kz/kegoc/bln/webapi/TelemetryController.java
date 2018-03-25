package kz.kegoc.bln.webapi;

import kz.kegoc.bln.entity.Telemetry;
import kz.kegoc.bln.repo.TelemetryRepo;
import kz.kegoc.bln.webapi.dto.TelemetryDto;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import static kz.kegoc.bln.util.Util.first;

@RestController
public class TelemetryController {
    private static final Logger logger = LoggerFactory.getLogger(TelemetryController.class);

    @PostConstruct
    private void init() {
        findById = repo::findOne;
        transformToEntity = t -> mapper.map(t, Telemetry.class);
        transformToDto = t -> mapper.map(t, TelemetryDto.class);
    }

    @GetMapping(value = "/telemetry", produces = "application/json")
    public List<TelemetryDto> getAll(
        @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime start,
        @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime end) {

        return repo.findByDateTimeBetween(start, end).stream()
            .map(transformToDto::apply)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/telemetry/{id}", produces = "application/json")
    public TelemetryDto getById(@PathVariable Long id) {
        return first(findById)
            .andThen(transformToDto)
            .apply(id);
    }


    private Function<Long, Telemetry> findById;
    private Function<TelemetryDto, Telemetry> transformToEntity;
    private Function<Telemetry, TelemetryDto> transformToDto;


    @Autowired
    private TelemetryRepo repo;

    @Autowired
    private DozerBeanMapper mapper;
}
