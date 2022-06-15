package br.com.project.pix.service;

import br.com.project.pix.dto.projection.PixAccountUserDetailsProjection;
import br.com.project.pix.exception.validations.DataNotFoundException;
import br.com.project.pix.exception.validations.FilterDateException;
import br.com.project.pix.exception.validations.FilterOnlyIdException;
import br.com.project.pix.exception.validations.KeyInactiveException;
import br.com.project.pix.model.PixAccountUserDetails;
import br.com.project.pix.repository.PixAccountUserDetailsRepository;
import br.com.project.pix.service.helper.ValidationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PixAccountUserDetailsService {

    private final PixAccountUserDetailsRepository pixAccountUserDetailsRepository;

    private final ValidationsService validationsService;

    public PixAccountUserDetails findById(Long id) {
        return pixAccountUserDetailsRepository.findById(id).orElseThrow(() -> {
            log.error("Pix account user details with id [{}] not found!", id);
            throw new DataNotFoundException();
        });
    }

    public PixAccountUserDetailsProjection findProjectionById(Long id) {
        return pixAccountUserDetailsRepository.findProjectionById(id).orElseThrow(() -> {
            log.error("Pix account user details with id [{}] not found!", id);
            throw new DataNotFoundException();
        });
    }

    public List<PixAccountUserDetailsProjection> findAllWithParameters(String keyValue, String agencyNumber, String accountNumber,
                                                                       String accountHolderName, String id, String dateActiveKey, String dateInactiveKey) {

        validateParameters(keyValue, agencyNumber, accountNumber, accountHolderName, id, dateActiveKey, dateInactiveKey);

        if (dateActiveKey != null) {
            dateActiveKey += "%";
        } else if (dateInactiveKey != null) {
            dateInactiveKey += "%";
        }

        return pixAccountUserDetailsRepository.findAllWithParameters(keyValue, agencyNumber, accountNumber, accountHolderName, id, dateActiveKey, dateInactiveKey);
    }

    private void validateParameters(String keyValue, String agencyNumber, String accountNumber, String accountHolderName, String id, String dateActiveKey, String dateInactiveKey) {
        if (id != null && (keyValue != null || agencyNumber != null || accountNumber != null || accountHolderName != null || dateActiveKey != null || dateInactiveKey != null))
            throw new FilterOnlyIdException();

        if (dateActiveKey != null && dateInactiveKey != null) throw new FilterDateException();
    }

    @Transactional
    public PixAccountUserDetails create(PixAccountUserDetails pixAccountUserDetails) {
        validationsService.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);
        pixAccountUserDetails.setId(UUID.randomUUID().hashCode() & Long.MAX_VALUE);
        pixAccountUserDetails.setInclusionKeyDateTime(LocalDateTime.now());
        return save(pixAccountUserDetails);
    }

    @Transactional
    public PixAccountUserDetails update(PixAccountUserDetails pixAccountUserDetails) {
        validationsService.validateUpdatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);
        return save(pixAccountUserDetails);
    }

    @Transactional
    public PixAccountUserDetails delete(PixAccountUserDetails pixAccountUserDetails) {
        if (pixAccountUserDetails.getInactiveKeyDateTime() != null) {
            throw new KeyInactiveException();
        }
        pixAccountUserDetails.setInactiveKeyDateTime(LocalDateTime.now());
       return save(pixAccountUserDetails);
    }

    private PixAccountUserDetails save(PixAccountUserDetails pixAccountUserDetails) {
        return pixAccountUserDetailsRepository.save(pixAccountUserDetails);
    }
}
