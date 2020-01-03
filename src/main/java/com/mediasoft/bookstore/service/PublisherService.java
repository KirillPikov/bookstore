package com.mediasoft.bookstore.service;

import com.mediasoft.bookstore.entity.Publisher;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublisherService {

    /**
     * Получение издателя по его ID.
     * @param publisherId ID издателя, которого хотим получить.
     * @return издателя с указанным ID.
     * @throws EntityNotFoundException
     */
    Publisher getPublisher(Long publisherId) throws EntityNotFoundException;

    /**
     * Получние списка всех издателей.
     * @return
     */
    List<Publisher> getPublishersPage(Pageable pageable);

    /**
     * Добавление нового издателя.
     * @param publisher новый издатель.
     */
    void addPublisher(Publisher publisher);

    /**
     * Изменение состояние издателя.
     * @param publisherId ID издателя.
     * @param publisher новое состояние издателя.
     * @throws EntityNotFoundException
     */
    void updatePublisher(Long publisherId, Publisher publisher) throws EntityNotFoundException;

    /**
     * Удаление издателя по ID.
     * @param publisherId ID издателя.
     * @throws EntityNotFoundException
     */
    void deletePublisher(Long publisherId) throws EntityNotFoundException;
}