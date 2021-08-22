package es.codeurjc.topics.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.topics.dtos.requests.TopicRequestDto;
import es.codeurjc.topics.dtos.requests.PostRequestDto;
import es.codeurjc.topics.dtos.responses.TopicDetailsResponseDto;
import es.codeurjc.topics.dtos.responses.TopicResponseDto;
import es.codeurjc.topics.dtos.responses.PostResponseDto;
import es.codeurjc.topics.services.TopicService;
import es.codeurjc.topics.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/topics")
public class TopicController {

    private TopicService topicService;
    private PostService postService;

    public TopicController(TopicService topicService, PostService postService) {
        this.topicService = topicService;
        this.postService = postService;
    }

    @Operation(summary = "Get all topics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all topics",
            content = {
                @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TopicResponseDto.class)))
            })
    })
    @GetMapping("/")
    public Collection <TopicResponseDto> getTopics() {
        return this.topicService.findAll();
    }

    @Operation(summary = "Get a topic by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the topic",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TopicDetailsResponseDto.class))
            }),
        @ApiResponse(responseCode = "400", description = "Invalid format id supplied",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Topic not found",
            content = @Content)
    })
    @GetMapping("/{topicId}")
    public TopicDetailsResponseDto getTopic(@Parameter(description = "id of topic to be searched") @PathVariable long topicId) {
        return this.topicService.findById(topicId);
    }

    @Operation(summary = "Create a new topic")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Topic to be created", required = true,
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TopicRequestDto.class)))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Created the topic",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TopicDetailsResponseDto.class))
            }),
        @ApiResponse(responseCode = "400", description = "Invalid topic attributes supplied",
            content = @Content)
    })
    @PostMapping("/")
    public TopicDetailsResponseDto createTopic(@Valid @RequestBody TopicRequestDto topicRequestDto) {
        return this.topicService.save(topicRequestDto);
    }

    @Operation(summary = "Add a post to a topic")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "post to be added", required = true,
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PostRequestDto.class)))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Added post to the topic",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PostResponseDto.class))
            }),
        @ApiResponse(responseCode = "400", description = "Invalid post attributes supplied",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Topic or user not found",
            content = @Content)
    })
    @PostMapping("/{topicId}/posts/")
    public PostResponseDto createPost(@Parameter(description = "identifier of the topic to which the post will be added") @PathVariable long topicId,
        @Valid @RequestBody PostRequestDto postRequestDto) {
        return this.postService.addPost(topicId, postRequestDto);
    }

    @Operation(summary = "Delete a post from a topic")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deleted post from the topic",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PostResponseDto.class))
            }),
        @ApiResponse(responseCode = "400", description = "Invalid format id supplied",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Post not found with passed topicId and postId",
            content = @Content)
    })
    @DeleteMapping("/{topicId}/posts/{postId}")
    public PostResponseDto deletePost(@Parameter(description = "identifier of the topic to which the post belongs") @PathVariable long topicId,
        @Parameter(description = "id of post to be deleted") @PathVariable long postId) {
        return this.postService.deletePost(topicId, postId);
    }

}
