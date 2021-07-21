# Moduli
- elastic7 **docker** http://localhost:5601/
- jaeger **docker** http://localhost:16686/
- otel-collector **docker**
- prometheus **docker** http://localhost:9090
- service-backend
- service-db
- service-poller
- skywalking **docker** http://localhost:8585/
- splunk **docker** http://localhost:8000
- zipkin **docker** http://localhost:9411/zipkin/

## Descrizione
I moduli 'docker' sono tool di visualizzazione, tranne otel-collector

I moduli service-backend, service-db, service-poller sono i progetti di test che sono stati instrumentati.

service-poller effettua delle chiamate schedulate verso service-backend e service-db, 
service-backend a sua volta quando riceve una chiamata, invoca service-db

otel-collector raccoglie le tracce degli instrumentatori e agent otel e le spedisce ad ogni visualizzatore

è configurato tramite [otel-collector/config.yaml](otel-collector/config.yaml) 
- processors: effettua modifiche custom sui tag 
- exporters: converte le tracce nei formati dei vari visualizzatori e definisce come inviarle ad ogni visualizzatore

## Instrumentazione

- service-poller: instrumentazione tramite librerie OTEL nel pom
- service-backend: instrumentazione tramite librerie OTEL nel pom
- service-db: instrumentazione agente, direttamente in riga di lancio (quindi senza impatti sul codice)

## Instrumentazione Skywalking

Per skywalking attualmente stiamo usando l'agente specifico skywalking-agent, in questo caso va aggiunto 
alla configurazione di lancio a tutti e tre i servizi

# How To

1. Lanciare i progetti docker: posizionarsi nella cartella contenente il docker-compose.yml
  e da riga di comando windows lanciare: docker compose up
    1. otel-collector
    1. i visualizzatori di interesse (jaeger, zipkin, ecc)
1. Lanciare i servizi di test instrumentati che generano gli span e le tracce
    - Generale
        1. lanciare i progetti service-backend e service-poller
        1. lanciare service-db con la run configuration modificata come da [screenshot otel java agent](service-db/Immagine 2021-07-15 230114.png)
    - Skywalking
        1. lanciare i progetti service-backend, service-poller e service-db con la run configuration modificata come da [screenshot skywalking agent](skywalking/Immagine 2021-07-19 075421.png)
    1. Accedendo ai visualizzatori attivati è possibile visulalizzare span e tracce generati dai servizi di test