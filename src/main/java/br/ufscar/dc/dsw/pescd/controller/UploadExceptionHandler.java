package br.ufscar.dc.dsw.pescd.controller;

import br.ufscar.dc.dsw.pescd.util.UploadUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.net.URI;

@ControllerAdvice(annotations = Controller.class)
public class UploadExceptionHandler {

    private static final String MENSAGEM_ARQUIVO_GRANDE =
            "O arquivo não pode ultrapassar o limite de " + UploadUtils.MAX_UPLOAD_LABEL + ".";

    // Captura uploads rejeitados pelo Spring antes de chegarem ao controller do formulário.
    @ExceptionHandler({MaxUploadSizeExceededException.class, MultipartException.class})
    public String tratarErroDeUpload(HttpServletRequest request) {
        RequestContextUtils.getOutputFlashMap(request).put("erro", MENSAGEM_ARQUIVO_GRANDE);
        RequestContextUtils.getOutputFlashMap(request).put("erroGeral", MENSAGEM_ARQUIVO_GRANDE);

        return "redirect:" + obterCaminhoSeguroDeRetorno(request);
    }

    private String obterCaminhoSeguroDeRetorno(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isBlank()) {
            return "/painel";
        }

        try {
            URI uri = URI.create(referer);
            String caminho = uri.getPath();
            // Evita redirecionar para valores externos ou malformados vindos do header Referer.
            if (caminho == null || caminho.isBlank() || !caminho.startsWith("/")) {
                return "/painel";
            }
            String query = uri.getQuery();
            return query == null ? caminho : caminho + "?" + query;
        } catch (IllegalArgumentException exception) {
            return "/painel";
        }
    }
}
