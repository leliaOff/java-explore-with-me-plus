package ru.practicum.ewm.services.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.exceptions.InvalidDataException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.mappers.CompilationMapper;
import ru.practicum.ewm.models.Compilation;
import ru.practicum.ewm.repository.CompilationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.toModel(newCompilationDto);
        compilation = compilationRepository.save(compilation);
        log.info("Compilation saved: {}", compilation);
        return CompilationMapper.toDto(compilation);
    }

    @Override
    public void deleteCompilation(Long compilationId) {
        compilationRepository.findById(compilationId).orElseThrow(() -> new NotFoundException("Compilation not found"));
        compilationRepository.deleteById(compilationId);
        log.info("Compilation deleted, ID : {}", compilationId);
    }

    @Override
    public CompilationDto updateCompilation(UpdateCompilationRequest updateCompilationRequest, Long compilationId) {
        Compilation currentCompilation = compilationRepository.findById(compilationId).orElseThrow(() -> new NotFoundException("Compilation not found"));
        Compilation compilation = CompilationMapper.mergeModel(currentCompilation, updateCompilationRequest);
        if (compilationRepository.existsByTitleAndIdNot(compilation.getTitle(), compilationId)) {
            throw new InvalidDataException("Compilation name already exists");
        }
        compilation = compilationRepository.save(compilation);
        log.info("Compilation updated, ID : {}", compilationId);
        return CompilationMapper.toDto(compilation);
    }
}
