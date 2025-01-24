package com.ourmenu.backend.domain.cache.api;

import com.ourmenu.backend.domain.cache.dto.MenuFolderIconResponse;
import com.ourmenu.backend.global.response.ApiResponse;
import com.ourmenu.backend.global.response.util.ApiUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache-data")
public class CacheController {

    @GetMapping
    public ApiResponse<List<MenuFolderIconResponse>> getCacheData() throws IOException {
        List<MenuFolderIconResponse> response = new ArrayList<>();
        List<String> filePaths = List.of("src/main/resources/static/menu-folder-icon/baeksuk.svg");

        for (String filePath : filePaths) {
            Path path = Paths.get(filePath);
            byte[] imageData = Files.readAllBytes(path);
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            MenuFolderIconResponse menuFolderIconResponse = MenuFolderIconResponse.of(filePath, base64Image);
            response.add(menuFolderIconResponse);
        }

        return ApiUtil.success(response);
    }

}
