package es.codeurjc.topics.services.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import es.codeurjc.topics.dtos.requests.TopicRequestDto;
import es.codeurjc.topics.dtos.responses.TopicDetailsResponseDto;
import es.codeurjc.topics.dtos.responses.TopicResponseDto;
import es.codeurjc.topics.exceptions.TopicNotFoundException;
import es.codeurjc.topics.models.Topic;
import es.codeurjc.topics.repositories.TopicRepository;
import es.codeurjc.topics.services.TopicService;

@Service
public class TopicServiceImpl implements TopicService {

    private Mapper mapper;
    private TopicRepository topicRepository;

    public TopicServiceImpl(Mapper mapper, TopicRepository topicRepository) {
        this.mapper = mapper;
        this.topicRepository = topicRepository;
    }

    public Collection<TopicResponseDto> findAll() {
        return this.topicRepository.findAll().stream()
                .map(topic -> this.mapper.map(topic, TopicResponseDto.class))
                .collect(Collectors.toList());
    }

    public TopicDetailsResponseDto save(TopicRequestDto topicRequestDto) {
        Topic topic = this.mapper.map(topicRequestDto, Topic.class);
        topic = this.topicRepository.save(topic);
        return this.mapper.map(topic, TopicDetailsResponseDto.class);
    }

    public TopicDetailsResponseDto findById(long topicId) {
        Topic topic = this.topicRepository.findById(topicId).orElseThrow(TopicNotFoundException::new);
        return this.mapper.map(topic, TopicDetailsResponseDto.class);
    }

}
