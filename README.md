# RPA-Monitor

**RPA-Monitor** é uma aplicação de monitoramento automatizado de URLs, que utiliza Selenium WebDriver e verifica o status HTTP das páginas monitoradas. O objetivo do projeto é garantir que as URLs especificadas estejam funcionando corretamente, verificando o status da página e capturando erros caso o carregamento falhe.

As informações são salvas em um banco de dados PostgreSQL, facilitando o acompanhamento dos resultados e análise do desempenho das páginas.

---

## Funcionalidades

- **Monitoramento de URLs**: A aplicação monitorará as URLs configuradas, verificando o status e registrando a execução.
- **Verificação de Status HTTP**: O sistema obtém o status HTTP da página e verifica possíveis falhas (ex: 404, 500).
- **Captura de Erros**: Caso o WebDriver falhe ao acessar uma URL, a mensagem de erro gerada é registrada.
- **Armazenamento de Logs**: Todos os dados de monitoramento (status e erros) são armazenados em um banco de dados PostgreSQL.
- **Agendamento de Tarefas**: O monitoramento é agendado utilizando o **Quartz Scheduler**, permitindo execuções periódicas.

---

## Tecnologias Utilizadas

- **Java 11+**: Linguagem de programação principal.
- **Selenium WebDriver**: Automação para abrir e verificar páginas da web.
- **Quartz Scheduler**: Framework para agendamento de tarefas.
- **PostgreSQL**: Banco de dados para armazenar logs de monitoramento.
- **JUnit**: Framework de testes unitários para validação do código.

---

## Estrutura do Projeto

### 1. MonitorJob
A classe principal responsável por monitorar as URLs configuradas. Ela executa as verificações HTTP e captura erros relacionados.

### 2. MonitorLogDAO
Responsável por acessar o banco de dados e armazenar os resultados do monitoramento (status HTTP e mensagens de erro).

### 3. WebDriverFactory
Faz a configuração e criação do WebDriver em modo headless (sem interface gráfica), para facilitar o monitoramento em servidores sem interface gráfica.

### 4. HTTPChecker
Uma classe utilitária que consulta o status HTTP das URLs, retornando o código de status (200, 404, 500, etc.).

---

## Como Configurar e Usar

### 1. Configuração do Banco de Dados

Primeiro, configure seu banco PostgreSQL e crie a tabela `monitor_log` com a seguinte estrutura:

```sql
CREATE TABLE monitor_log (
    id SERIAL PRIMARY KEY,
    ativo VARCHAR(255),
    data_execucao TIMESTAMP,
    status INT,
    error_message TEXT
);
