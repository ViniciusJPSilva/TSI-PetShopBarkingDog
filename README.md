<h1 align="center"> PetShop Cão Q-Late </h1>

![image](https://github.com/ViniciusJPSilva/TSI-PetShopBarkingDog/assets/81810017/abc24388-91c0-43fb-8c2e-3f0d323f690c)

<hr>
<br>


<h2>Descrição</h2>

<p align="justify">
O Cão Q-Late é um sistema web desenvolvido para auxiliar no gerenciamento de serviços prestados a cães em um PetShop. O sistema atende às necessidades de clientes e funcionários, oferecendo funcionalidades para cadastro, agendamento, consulta e edição de informações, além de geração de relatórios.
</p>

<br>


<h2>Requisitos</h2>

- Java versão 17 ou superior

- Apache Tomcat versão 10.1

- Dynamic Web Module versão 6.0

- JPA 2.0

- PSQL 14 ou superior

<br>


<h2>Funcionalidades</h2>

<h3>Clientes:</h3>

 - Cadastro e login
 - Cadastro de cães
 - Alteração de dados de contato e senha
 - Agendamento de serviços
 - Listagem de serviços agendados e ainda não executados (com opção de cancelamento)
 - Listagem de serviços já realizados

<h3>Funcionários:</h3>

 - Cadastro de serviços
 - Alteração de valor de serviços
 - Listagem da agenda de serviços
 - Lançamento de serviços realizados
 - Geração de relatório de serviços prestados

<br>
<hr>
<br>


<h2>Instruções de Execução</h2>

<h3>1. Backup do Banco de Dados</h3>

O arquivo de backup do banco de dados (`petshop_backup.sql`) está localizado no diretório `DataBase`.

- Restaurar o backup para gerar as tabelas é opcional, pois o sistema está programado para criá-las automaticamente ao iniciar. Basta criar o banco de dados e alterar o nome no algoritmo, conforme as instruções abaixo.

<h3>2. Configuração da Aplicação</h3>

Certifique-se de alterar os dados necessários para a execução da aplicação. O arquivo de configuração é `br/vjps/tsi/psbd/system/SystemSettings.java`. Você pode alterar:

- **Nome do Banco de Dados**: Caso necessário.
  - **Padrão**:
    - Nome: `<petshop>`

  OBS.: O nome do banco, usuário e senha também devem ser alterados no arquivo` src/main/java/META-INF/persistence.xml`.

- **Modo de Envio de E-mails**:
  - Enviar para um e-mail de testes (habilitado).
  - Enviar para os e-mails dos pacientes cadastrados.
  - Não enviar nenhum e-mail.

- **E-mail (Gmail) para Integração com Aplicações Web**: Altere também a senha correspondente.

- **URL Base da Aplicação**: Utilizada para a confirmação de cadastro (envio do token).
  - URL Padrão: `http://localhost:3000/PetShopBarkingDog`

Obs.: Todas as informações necessárias estão comentadas no arquivo` SystemSettings.java`.

<h3>3. Login do Cliente</h3>

O login do cliente no sistema é realizado por meio do e-mail e da senha. Não há clientes pré-cadastrados, mas eles podem ser cadastrados durante o uso da aplicação.

<h3>4. Login do Funcionário (Administrador)</h3>

O login do funcionário (administrador) no sistema é realizado por meio do login e da senha. Há apenas um funcionário, que é inserido automaticamente no banco de dados durante a inicialização do sistema.

<table>
  <tr>
    <th>LOGIN</th>
    <th>SENHA</th>
  </tr>
  <tr>
    <td>admin</td>
    <td>admin</td>
  </tr>
</table>


