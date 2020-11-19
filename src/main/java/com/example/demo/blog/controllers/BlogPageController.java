package com.example.demo.blog.controllers;

import com.example.demo.blog.models.Post;
import com.example.demo.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class BlogPageController {

    @Autowired
    private PostRepository postRepo;

    @GetMapping("/")
    public String blog(Model model) {
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("title", "Main Page");
        return "blog-main-page";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add-page";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(Model model, Post post) {
        postRepo.save(post);
        return "redirect:/";
    }

    @GetMapping("/blog/{id}")
    public String blogFullPage(@PathVariable(value = "id") long id, Model model) {
        if (!postRepo.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> optionalPost = postRepo.findById(id);
        Post post = optionalPost.get();
        model.addAttribute("post", post);
        return "blog-full-topic";
    }

    @GetMapping("/blog/{id}/edit")
    public String postEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepo.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> optionalPost = postRepo.findById(id);
        Post post = optionalPost.get();
        model.addAttribute("post", post);
        return "blog-edit-page";
    }

    @PostMapping("/blog/{id}/edit")
    public String postUpdate(@PathVariable(value = "id") long id, Model model, Post post) {
        postRepo.save(post);
        return "redirect:/";
    }

    @PostMapping("/blog/{id}/delete")
    public String postDelete(@PathVariable(value = "id") long id, Model model) {
        Optional<Post> optionalPost = postRepo.findById(id);
        Post post = optionalPost.get();
        postRepo.delete(post);
        return "redirect:/";
    }
}
