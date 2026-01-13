# Smart-Appointment-Scheduling-System
post:https://localhost:8443/api/auth/signup
Body 
{
  "username": "ahmad",
  "email": "azytwn242@gmail.com",
  "password": "22222222"
}
===========================================
post:https://localhost:8443/api/auth/login
Body
{
  "username": "ahmad",
  "password": "22222222"
}
============================================
create services
post:https://localhost:8443/api/services
Body
{
  "name": "IT suport",
  "duration": 15,
  "price": 40,
  "providerId": 2
}
===========================================
ubdate
put:https://localhost:8443/api/services/{id}
Body
{
  "duration": 5,
  "price": 10
}
===========================================
post:https://localhost:8443/api/schedule
Body
{
  "dayOfWeek": "FRIDAY",
  "holiday": true
}
===========================================
book appointment
post:https://localhost:8443/api/appointments/book
Body
{
  "serviceId": "1",
  "date": "2026-01-07",
  "startTime": "11:00"
}
===========================================
ubdate status for appointment
put:https://localhost:8443/api/appointments/{id}/status?status={status}
===========================================
Bonus // get from Gemini
Get:https://localhost:8443/api/appointments/suggest?date={date}&serviceId={serviceId}
===========================================
run websocket 
wss://localhost:8443/ws/websocket
put in send message:
CONNECT
accept-version:1.2
host:localhost

\u0000
==========================================
check the websocket work:
GET     https://localhost:8443/ws
=========================================

Yamen alsyoufie:dataBase + https + logic + websocket + AOP+Email request 
=====================================================================
Ahmad Zetton: logic + stress testing+book using Google gemini + change the state of the appointments
=====================================================================
Amjad Abu bakar: login + singup + jwt security
=====================================================================
