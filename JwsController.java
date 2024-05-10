import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class JwsController {

    private static final String SECRET_KEY = "your_secret_key"; // Change this with your actual secret key

    @PostMapping("/sendJwsMessage")
    public ResponseEntity<String> sendJwsMessage(@RequestBody MemberNoticeRequest request) {
        // Validate request against JSON schema if needed

        // Create JWS
        String jws = createJws(request);

        // Return JWS
        return new ResponseEntity<>(jws, HttpStatus.OK);
    }

    private String createJws(MemberNoticeRequest request) {
        // Convert MemberNoticeRequest to JSON and create JWS
        // For simplicity, let's assume that the entire request is the subject of the JWS
        return Jwts.builder()
                .setSubject(convertToJson(request))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private String convertToJson(Object object) {
        // Convert object to JSON string (you can use your preferred JSON library for this)
        // For simplicity, let's assume that the object is converted to JSON using Jackson ObjectMapper
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }
}
