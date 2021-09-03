# Moduli
- elastic7 `docker` http://localhost:5601/
- jaeger `docker` http://localhost:16686/
- otel-collector `docker`
- prometheus `docker` http://localhost:9090
- service-backend
- service-db
- service-poller
- skywalking `docker` http://localhost:8585/
- splunk `docker` http://localhost:8000
- zipkin `docker` http://localhost:9411/zipkin/

## Descrizione
I moduli `docker` sono tool di visualizzazione, tranne **otel-collector**.

I moduli **service-backend**, **service-db**, **service-poller** sono i progetti di test che sono stati instrumentati.

- **service-poller** effettua delle chiamate schedulate verso **service-backend** e **service-db**, 
**service-backend** a sua volta quando riceve una chiamata, invoca **service-db**.

**otel-collector** raccoglie le tracce degli instrumentatori e agent _otel_ e le spedisce ad ogni visualizzatore.

È configurato tramite [otel-collector/config.yaml](otel-collector/config.yaml) 
- _processors_: effettua modifiche custom sui tag 
- _exporters_: converte le tracce nei formati dei vari visualizzatori e definisce come inviarle ad ogni visualizzatore

## Instrumentazione

- **service-poller**: instrumentazione tramite librerie OTEL nel pom
- **service-backend**: instrumentazione tramite librerie OTEL nel pom
- **service-db**: instrumentazione agente, direttamente in riga di lancio (quindi senza impatti sul codice)

## Instrumentazione Skywalking

Per `Skywalking` attualmente stiamo usando l'agente specifico _skywalking-agent_, in questo caso va aggiunto 
alla configurazione di lancio a tutti e tre i servizi

# How To

1. Lanciare i progetti `docker`: posizionarsi nella cartella contenente il `docker-compose.yml` e da riga di comando windows lanciare:

        docker compose up

  1. **otel-collector**
  1. I visualizzatori di interesse (**jaeger**, **zipkin**, ecc)
1. Lanciare i servizi di test instrumentati che generano gli span e le tracce
  - Generale
    1. Lanciare i progetti **service-backend** e **service-poller**. Tramite IDE oppure con il comando
    
            mvn spring-boot:run
    
    1. Lanciare da IDE **service-db** con la run configuration modificata come segue:

    ![screenshot otel java agent](service-db/Immagine%202021-07-15%20230114.png)

        -javaagent:opentelemetry-javaagent-all.jar -Dotel.service.name=service-db

  - Skywalking
    1. Lanciare i progetti **service-backend**, **service-poller** e **service-db** con la run configuration modificata come segue

    ![screenshot skywalking agent](skywalking/Immagine%202021-07-19%20075421.png)

1. Accedendo ai visualizzatori attivati è possibile visulalizzare span e tracce generati dai servizi di test
