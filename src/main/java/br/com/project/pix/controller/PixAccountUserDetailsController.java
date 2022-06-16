package br.com.project.pix.controller;

import br.com.project.pix.dto.CreatePixAccountUserDetailsDTO;
import br.com.project.pix.dto.UpdatePixAccountUserDetailsDTO;
import br.com.project.pix.dto.projection.PixAccountUserDetailsProjection;
import br.com.project.pix.model.PixAccountUserDetails;
import br.com.project.pix.service.PixAccountUserDetailsService;
import br.com.project.pix.utils.NonNullBeanUtilsBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static br.com.project.pix.Paths.PIX_ACCOUNT_USER_DETAILS;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping(path = PIX_ACCOUNT_USER_DETAILS)
@RequiredArgsConstructor
public class PixAccountUserDetailsController extends AbstractRestController<Long> {

    private final ProjectionFactory projectionFactory;

    private final PixAccountUserDetailsService pixAccountUserDetailsService;

    private final NonNullBeanUtilsBean utilsBean = new NonNullBeanUtilsBean();

    @ResponseStatus(CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PixAccountUserDetailsProjection> createPixAccountUserDetails(
            @RequestBody @Valid @NotNull(message = "Request body 'pixAccountUserDetails' is required") CreatePixAccountUserDetailsDTO createPixAccountUserDetailsDTO) {

        PixAccountUserDetails pixAccountUserDetails = new PixAccountUserDetails();
        BeanUtils.copyProperties(createPixAccountUserDetailsDTO, pixAccountUserDetails);

        log.info("Creating new Pix Account User Detail");
        PixAccountUserDetails response = pixAccountUserDetailsService.create(pixAccountUserDetails);

        return newCreatedResponse(response.getId(),
                projectionFactory.createProjection(PixAccountUserDetailsProjection.class, response));
    }

    @ResponseStatus(OK)
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PixAccountUserDetailsProjection> updatePixAccountUserDetails(
            @PathVariable("id") Long id,
            @RequestBody @Valid @NotNull(message = "Request body 'pixAccountUserDetails' is required") UpdatePixAccountUserDetailsDTO updatePixAccountUserDetailsDTO)
            throws InvocationTargetException, IllegalAccessException {

        log.info("Updating Pix Account User Detail with id [{}]", id);
        PixAccountUserDetails pixAccountUserDetails = pixAccountUserDetailsService.findById(id);

        utilsBean.copyProperties(pixAccountUserDetails, updatePixAccountUserDetailsDTO);
        PixAccountUserDetails response = pixAccountUserDetailsService.update(pixAccountUserDetails);

        return newUpdateResponse(
                projectionFactory.createProjection(PixAccountUserDetailsProjection.class, response));
    }

    @ResponseStatus(OK)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PixAccountUserDetails> deletePixAccountUserDetails(@PathVariable(value = "id") Long id) {

        log.info("Deleting Pix Account User Detail with id [{}]", id);
        PixAccountUserDetails pixAccountUserDetails = pixAccountUserDetailsService.findByIdForDelete(id);
        PixAccountUserDetails accountUserDetailsDeleted = pixAccountUserDetailsService.delete(pixAccountUserDetails);

        return ok(accountUserDetailsDeleted);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PixAccountUserDetailsProjection> findById(@PathVariable(value = "id") Long id) {

        log.info("Finding Pix Account User Detail by id [{}]...", id);

        return ok(pixAccountUserDetailsService.findProjectionById(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PixAccountUserDetailsProjection>> findAll(
            @RequestParam(required = false) String keyValue,
            @RequestParam(required = false) String agencyNumber,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String accountHolderName,
            @RequestParam(required = false) String idPixAccountUserDetail,
            @RequestParam(required = false) String dateActiveKey,
            @RequestParam(required = false) String dateInactiveKey) {

        log.info("Finding Pix Account User Details...");

        List<PixAccountUserDetailsProjection> responseAll = pixAccountUserDetailsService.findAllWithParameters(keyValue, agencyNumber, accountNumber, accountHolderName, idPixAccountUserDetail,
                dateActiveKey, dateInactiveKey);

        if (responseAll.isEmpty()) {
            return notFound().build();
        }

        return ok(responseAll);
    }

}
