# Vice City Nights (Jenkins CI/CD practice app)

A neon-sunset, retro-themed Spring Boot site for learning Jenkins pipelines.
Unofficial fan-made demo; no third-party game assets are used.

## Run locally
```bash
mvn spring-boot:run          # http://localhost:8080
mvn clean verify             # build + tests + JaCoCo report
```

## Pipeline stages (Jenkinsfile)
Checkout, Build & Unit Test, Checkstyle, SonarQube Analysis, Quality Gate,
Trivy FS scan, Docker build, Trivy image scan, Deploy (port 8081), Smoke test.

## Jenkins prerequisites
- Tools named `MAVEN`, `JDK` (17+), `SONAR`; SonarQube server named `sonarcodeserver`
- SonarQube webhook to `/sonarqube-webhook/` on Jenkins (set up earlier)
- `docker` CLI and `trivy` installed in the Jenkins container, plus access to
  the host Docker socket (`-v /var/run/docker.sock:/var/run/docker.sock`)
- `curl` available in the Jenkins container for the smoke test

## Endpoints
- `/` themed landing page
- `/api/features` JSON list
- `/actuator/health` health probe
