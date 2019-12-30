package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.Publisher;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.repository.PublisherRepository;
import com.mediasoft.bookstore.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    /**
     * Получение издателя по его ID.
     *
     * @param publisherId ID издателя, которого хотим получить.
     * @return издателя с указанным ID.
     * @throws EntityNotFoundException
     */
    @Override
    public Publisher getPublisher(Long publisherId) throws EntityNotFoundException {
        return publisherRepository.findById(publisherId)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("Publisher with id = " + publisherId + " - not found.");
                        }
                );
    }

    /**
     * Получние списка всех издателей.
     *
     * @param pageable
     * @return
     */
    @Override
    public List<Publisher> getPublishersPage(Pageable pageable) {
        return publisherRepository.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * Добавление нового издателя.
     *
     * @param publisher новый издатель.
     * @return нового издателя.
     */
    @Override
    public void addPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    /**
     * Изменение состояние издателя.
     *
     * @param publisherId ID издателя.
     * @param publisher   новое состояние издателя.
     * @throws EntityNotFoundException
     */
    @Override
    public void updatePublisher(Long publisherId, Publisher publisher) throws EntityNotFoundException {
        publisher.setId(publisherId);
        publisherRepository.findById(publisherId)
                .ifPresentOrElse(
                        repoPublisher -> {
                            repoPublisher = publisher;
                            publisherRepository.save(repoPublisher);
                        }, () -> {
                            throw new EntityNotFoundException("Publisher with id = " + publisher.getId() + " - not found.");
                        }
                );
    }

    /**
     * Удаление издателя по ID.
     *
     * @param publisherId ID издателя.
     * @throws EntityNotFoundException
     */
    @Override
    public void deletePublisher(Long publisherId) throws EntityNotFoundException {
        /* Проверяем существование издателя с таким ID */
        if(publisherRepository.existsById(publisherId)) {
            /* Если существует - удаляем */
            publisherRepository.deleteById(publisherId);
        } else {
            /* Иначе выбрасываем исключение */
            throw new EntityNotFoundException("Publisher with id = " + publisherId + " - not found.");
        }
    }
}