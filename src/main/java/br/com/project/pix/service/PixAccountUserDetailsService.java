package br.com.project.pix.service;

import br.com.project.pix.exception.validations.DataNotFoundException;
import br.com.project.pix.exception.validations.KeyInactiveException;
import br.com.project.pix.model.PixAccountUserDetails;
import br.com.project.pix.repository.PixAccountUserDetailsRepository;
import br.com.project.pix.service.helper.ValidationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PixAccountUserDetailsService {

    private final PixAccountUserDetailsRepository pixAccountUserDetailsRepository;

    private final ValidationsService validationsService;

    public PixAccountUserDetails findById(long id) {
        return pixAccountUserDetailsRepository.findById(id).orElseThrow(() -> {
            log.error("Pix account user details with id [{}] not found!", id);
            throw new DataNotFoundException();
        });
    }

    @Transactional
    public PixAccountUserDetails create(PixAccountUserDetails pixAccountUserDetails) {
        validationsService.validatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);
        pixAccountUserDetails.setId(UUID.randomUUID().getMostSignificantBits());
        pixAccountUserDetails.setInclusionKeyDateTime(LocalDateTime.now());
        return save(pixAccountUserDetails);
    }

    @Transactional
    public void update(PixAccountUserDetails pixAccountUserDetails) {
        save(pixAccountUserDetails);
    }

    @Transactional
    public void delete(PixAccountUserDetails pixAccountUserDetails) {
       if(pixAccountUserDetails.getInactiveKeyDateTime() != null){
           throw new KeyInactiveException();
       }
        pixAccountUserDetails.setInactiveKeyDateTime(LocalDateTime.now());
        save(pixAccountUserDetails);
    }

    private PixAccountUserDetails save(PixAccountUserDetails pixAccountUserDetails) {
        return pixAccountUserDetailsRepository.save(pixAccountUserDetails);
    }
}
