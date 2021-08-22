package es.codeurjc.topics.services.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import es.codeurjc.topics.dtos.requests.PostRequestDto;
import es.codeurjc.topics.dtos.responses.PostResponseDto;
import es.codeurjc.topics.dtos.responses.UserPostResponseDto;
import es.codeurjc.topics.exceptions.TopicNotFoundException;
import es.codeurjc.topics.exceptions.PostNotFoundException;
import es.codeurjc.topics.exceptions.UserNotFoundException;
import es.codeurjc.topics.models.Topic;
import es.codeurjc.topics.models.Post;
import es.codeurjc.topics.models.User;
import es.codeurjc.topics.repositories.TopicRepository;
import es.codeurjc.topics.repositories.PostRepository;
import es.codeurjc.topics.repositories.UserRepository;
import es.codeurjc.topics.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    private Mapper mapper;
    private PostRepository postRepository;
    private TopicRepository topicRepository;
    private UserRepository userRepository;

    public PostServiceImpl(Mapper mapper, PostRepository postRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.postRepository = postRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public PostResponseDto addPost(long topicId, PostRequestDto postRequestDto) {
        System.out.println("🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀" + postRequestDto.toString());
        Topic topic = this.topicRepository.findById(topicId).orElseThrow(TopicNotFoundException::new);
        User user = this.userRepository.findById(postRequestDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Post post = this.mapper.map(postRequestDto, Post.class);
        post.setTopic(topic);
        post.setUser(user);
        post = this.postRepository.save(post);
        return this.mapper.map(post, PostResponseDto.class);
    }

    public PostResponseDto deletePost(long topicId, long postId) {
        Post post = this.postRepository.findByTopicIdAndId(topicId, postId)
                .orElseThrow(PostNotFoundException::new);
        this.postRepository.delete(post);
        return this.mapper.map(post, PostResponseDto.class);
    }

    public Collection<UserPostResponseDto> getPosts(Long userId) {
        return this.postRepository.findByUserId(userId).stream()
                .map(post -> this.mapper.map(post, UserPostResponseDto.class))
                .collect(Collectors.toList());
    }

}
