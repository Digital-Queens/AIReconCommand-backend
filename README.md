# 🛰️ AI Recon Command – Backend

Warstwa serwerowa systemu **AI Recon Command**, odpowiedzialna za odbiór ramek z dronów i kamer, integrację z modelem AI oraz przesyłanie wyników detekcji w czasie rzeczywistym.

---

## ⚙️ Opis

Backend stanowi centrum komunikacji między frontendem a modelem AI.  
Umożliwia:
- odbiór ramek obrazu (WebSocket `/app/frame`),
- analizę przez model YOLO (Python),
- publikację wyników przez `/topic/detections`,
- zapis i logowanie zdarzeń w MongoDB.

---

## 🧠 Technologie
- Java 21  
- Spring Boot 3+  
- Spring WebSocket (STOMP)  
- PostgreSQL 
- Lombok  
- Maven  

---
