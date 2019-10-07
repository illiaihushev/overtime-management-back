package app.service;

import app.web.dto.InfoToUpdateTitlesDTO;
import app.web.dto.RelatedInfoDTO;

public interface UpdateInfoService {
    InfoToUpdateTitlesDTO getIrrelevantFrom(InfoToUpdateTitlesDTO titles);

    void saveExistingAppData(RelatedInfoDTO data);
}
