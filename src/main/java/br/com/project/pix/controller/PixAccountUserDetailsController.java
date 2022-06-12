package br.com.project.pix.controller;

import br.com.project.pix.dto.PixAccountUserDetailsDTO;
import br.com.project.pix.dto.projection.PixAccountUserDetailsProjection;
import br.com.project.pix.model.PixAccountUserDetails;
import br.com.project.pix.service.PixAccountUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static br.com.project.pix.Paths.PIX_ACCOUNT_USER_DETAILS;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = PIX_ACCOUNT_USER_DETAILS)
@RequiredArgsConstructor
public class PixAccountUserDetailsController extends AbstractRestController<Long> {

    private final ProjectionFactory projectionFactory;

    private final PixAccountUserDetailsService pixAccountUserDetailsService;

    @ResponseStatus(CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PixAccountUserDetailsProjection> createPixAccountUserDetails(
            @RequestBody @Valid @NotNull(message = "Request body 'pixAccountUserDetails' is required") PixAccountUserDetailsDTO pixAccountUserDetailsDTO) {

        PixAccountUserDetails pixAccountUserDetails = new PixAccountUserDetails();
        BeanUtils.copyProperties(pixAccountUserDetailsDTO, pixAccountUserDetails);

        log.info("Creating new Pix Account User Detail");
        PixAccountUserDetails response = pixAccountUserDetailsService.create(pixAccountUserDetails);

        return newCreatedResponse(response.getId(),
                projectionFactory.createProjection(PixAccountUserDetailsProjection.class, response));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePixAccountUserDetails(@PathVariable(value = "id") Long id) {

        log.info("Deleting Pix Account User Detail with id [{}]", id);
        PixAccountUserDetails pixAccountUserDetails = pixAccountUserDetailsService.findById(id);
        pixAccountUserDetailsService.delete(pixAccountUserDetails);

        return ResponseEntity.ok().build();
    }
}
