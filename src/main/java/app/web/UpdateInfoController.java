package app.web;

import app.service.PreAuthInfoService;
import app.service.UpdateInfoService;
import app.web.dto.InfoToUpdateTitlesDTO;
import app.web.dto.RelatedInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/info")
public class UpdateInfoController {
    @Autowired
    UpdateInfoService updateInfoService;

    @Autowired
    PreAuthInfoService preAuthInfoService;

    @PostMapping("titles")
    public ResponseEntity<InfoToUpdateTitlesDTO> takeTitles(@RequestBody InfoToUpdateTitlesDTO titles){
        preAuthInfoService.updateInitator(titles.getInitiator());


        return ResponseEntity.ok(updateInfoService.getIrrelevantFrom(titles));
    }

    @PostMapping("data")
    public ResponseEntity<String> takeData(@RequestBody RelatedInfoDTO data){
        updateInfoService.saveExistingAppData(data);
        return ResponseEntity.ok("OK");
    }

}
