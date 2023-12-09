package com.moz.ates.traffic.policewebapp.demo;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api")
public class ApiController {

    @GetMapping(value="/get_plate_number")
    public String viewApiTest(Model model) {

        return "views/api/apiTest";
    }

    @PostMapping(value="/get_plate_number", produces = "application/json")
    public ResponseEntity<?> getPlateNumber(@RequestParam("file") MultipartFile file) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");

        okhttp3.RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file","plate.jpeg",
                        okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/octet-stream"), file.getBytes()))
                .addFormDataPart("name","123")
                .build();
        Request request = new Request.Builder()
                .url("http://218.152.205.17:8086/plate_number")
//                .url("http://127.0.0.1:5000/plate_number")
                .method("POST", body)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());
//        assert response.body() != null;
        String resBody = response.body().string();
        return new ResponseEntity<String>(resBody, HttpStatus.OK);
//        return response;
    }
}
