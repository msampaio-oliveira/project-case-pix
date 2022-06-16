package br.com.project.pix.controller;

import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@NoArgsConstructor
public abstract class AbstractRestController<T> {

    protected URI getCreatedURI(T id) {
        return getCurrentRequestUriBuilder().path("/{id}").buildAndExpand(id).toUri();
    }

    protected <B> ResponseEntity<B> newCreatedResponse(T id, B body) {
        URI location = this.getCreatedURI(id);
        return created(location).body(body);
    }

    protected <B> ResponseEntity<B> newUpdateResponse(B body) {
        return ok(body);
    }

    protected ServletUriComponentsBuilder getCurrentRequestUriBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequest();
    }
}
