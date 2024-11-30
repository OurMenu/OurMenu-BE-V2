package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/menu-folders")
public class MenuFolderController {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveMenuFolder(@RequestPart("data") SaveMenuFolderRequest request,
                                 @RequestPart("menuFolderImg") MultipartFile menuFolderImg) {
        
        return "OK";
    }
}
