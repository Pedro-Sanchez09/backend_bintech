package com.bintech.backend_ecommerce.services;

import com.bintech.backend_ecommerce.entity.Imagen;
import com.bintech.backend_ecommerce.entity.Producto;
import com.bintech.backend_ecommerce.repository.ImagenRepository;
import com.bintech.backend_ecommerce.utility.answers.ImagenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImagenService {
    @Autowired
    private ImagenRepository imagenRepository;

    public String subirImagen(MultipartFile file, Producto producto) throws IOException {

        Imagen imagen = imagenRepository.save(Imagen.builder()
                .nombre(file.getOriginalFilename())
                .tipo(file.getContentType())
                .idProducto(producto)
                .imagenData(ImagenUtil.compressImagen(file.getBytes())).build());

        if (imagen != null) {
            return "Imagen agregada: " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] descargarImagen(String nombreImagen) {
        Optional<Imagen> imagen = imagenRepository.findByNombre(nombreImagen);
        if(!imagen.isPresent()){
            return  null;
        }
        byte[] img = ImagenUtil.decompressImagen(imagen.get().getImagenData());
        return img;
    }
}
