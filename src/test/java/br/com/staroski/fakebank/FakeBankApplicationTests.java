package br.com.staroski.fakebank;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FakeBankApplicationTests {

    private static final String JSON = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void testCriarClientes() throws Exception {
        String url = "/clientes/criar";

        String cliente1 = novoClienteJson("12345678901", "João da Silva", "joaozinho","123456");
        String cliente2 = novoClienteJson("10987654321", "Maria dos Santos", "mariazinha","654321");
        
        mockMvc.perform(post(url).contentType(JSON).content(cliente1)).andExpect(status().isCreated());
        mockMvc.perform(post(url).contentType(JSON).content(cliente2)).andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void testCriarAgencias() throws Exception {
        String url = "/agencias/criar";

        String agencia1 = novaAgenciaJson("Agência 1");
        String agencia2 = novaAgenciaJson("Agência 2");
        
        mockMvc.perform(post(url).contentType(JSON).content(agencia1)).andExpect(status().isCreated());
        mockMvc.perform(post(url).contentType(JSON).content(agencia2)).andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    void testCriarContas() throws Exception {
        String url = "/contas/criar";

        String conta1 = novaContaJson(1, 1, 500.0);
        String conta2 = novaContaJson(2, 2, 250.0);
        
        mockMvc.perform(post(url).contentType(JSON).content(conta1)).andExpect(status().isCreated());
        mockMvc.perform(post(url).contentType(JSON).content(conta2)).andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    void testListarClientes() throws Exception {
        String url = "/clientes/listar";

        mockMvc.perform(get(url).contentType(JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].codigo", is(1)))
        .andExpect(jsonPath("$[0].cpf", is("12345678901")))
        .andExpect(jsonPath("$[0].nome", is("João da Silva")))
        .andExpect(jsonPath("$[0].login", is("joaozinho")))
        .andExpect(jsonPath("$[1].codigo", is(2)))
        .andExpect(jsonPath("$[1].cpf", is("10987654321")))
        .andExpect(jsonPath("$[1].nome", is("Maria dos Santos")))
        .andExpect(jsonPath("$[1].login", is("mariazinha")));
    }
    
    @Test
    @Order(5)
    void testListarAgencias() throws Exception {
        String url = "/agencias/listar";
        
        mockMvc.perform(get(url).contentType(JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].numero", is(1)))
        .andExpect(jsonPath("$[0].nome", is("Agência 1")))
        .andExpect(jsonPath("$[1].numero", is(2)))
        .andExpect(jsonPath("$[1].nome", is("Agência 2")));
    }
    
    @Test
    @Order(5)
    void testListarContas() throws Exception {
        String url = "/contas/listar";
        
        mockMvc.perform(get(url).contentType(JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].numeroAgencia", is(1)))
        .andExpect(jsonPath("$[0].numeroConta", is(1)))
        .andExpect(jsonPath("$[0].codigoCliente", is(1)))
        .andExpect(jsonPath("$[0].saldo", is(500.0)))
        .andExpect(jsonPath("$[1].numeroAgencia", is(2)))
        .andExpect(jsonPath("$[1].numeroConta", is(2)))
        .andExpect(jsonPath("$[1].codigoCliente", is(2)))
        .andExpect(jsonPath("$[1].saldo", is(250.0)));
    }
    
    @Test
    @Order(6)
    void testDepositar() throws Exception {
        String url = "/transacoes/depositar";
        
        String deposito = valorDepositoSaqueJson(1, 1, 100.0);
        
        mockMvc.perform(post(url).contentType(JSON).content(deposito))
        .andExpect(jsonPath("numeroAgencia", is(1)))
        .andExpect(jsonPath("numeroConta", is(1)))
        .andExpect(jsonPath("codigoCliente", is(1)))
        .andExpect(jsonPath("valor", is(100.0)));
    }
    
    @Test
    @Order(7)
    void testSacar() throws Exception {
        String url = "/transacoes/sacar";
        
        String saque = valorDepositoSaqueJson(1, 1, 50.0);
        
        mockMvc.perform(post(url).contentType(JSON).content(saque))
        .andExpect(jsonPath("numeroAgencia", is(1)))
        .andExpect(jsonPath("numeroConta", is(1)))
        .andExpect(jsonPath("codigoCliente", is(1)))
        .andExpect(jsonPath("valor", is(-50.0)));
    }
    
    @Test
    @Order(8)
    void testTransferir() throws Exception {
        String url = "/transacoes/transferir";
        
        String transferencia = valorTransferenciaJson(1, 1, 10.0, 2, 2);
        
        mockMvc.perform(post(url).contentType(JSON).content(transferencia))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].numeroAgencia", is(1)))
        .andExpect(jsonPath("$[0].numeroConta", is(1)))
        .andExpect(jsonPath("$[0].codigoCliente", is(1)))
        .andExpect(jsonPath("$[0].valor", is(-10.0)))
        .andExpect(jsonPath("$[1].numeroAgencia", is(2)))
        .andExpect(jsonPath("$[1]numeroConta", is(2)))
        .andExpect(jsonPath("$[1]codigoCliente", is(2)))
        .andExpect(jsonPath("$[1]valor", is(10.0)));    
    }
    
    @Test
    @Order(9)
    void testListarTransacoes() throws Exception {
        String url = "/transacoes/listar";
        
        String consulta = consultaContaJson(1, 1);
        
        mockMvc.perform(post(url).contentType(JSON).content(consulta))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].numeroAgencia", is(1)))
        .andExpect(jsonPath("$[0].numeroConta", is(1)))
        .andExpect(jsonPath("$[0].codigoCliente", is(1)))
        .andExpect(jsonPath("$[0].valor", is(100.0)))
        .andExpect(jsonPath("$[1].numeroAgencia", is(1)))
        .andExpect(jsonPath("$[1].numeroConta", is(1)))
        .andExpect(jsonPath("$[1].codigoCliente", is(1)))
        .andExpect(jsonPath("$[1].valor", is(-50.0)))
        .andExpect(jsonPath("$[2].numeroAgencia", is(1)))
        .andExpect(jsonPath("$[2].numeroConta", is(1)))
        .andExpect(jsonPath("$[2].codigoCliente", is(1)))
        .andExpect(jsonPath("$[2].valor", is(-10.0)));    
    }

    private String consultaContaJson(long numeroAgencia, long numeroConta) {
        return "{"
             + "    \"numeroAgencia\": \"" + numeroAgencia + "\","
             + "    \"numeroConta\": \"" + numeroConta + "\""
             + "}";
    }
    
    private String valorTransferenciaJson(long agenciaOrigem, long contaOrigem, double valor, long agenciaDestino, long contaDestino) {
        return "{"
             + "    \"agenciaOrigem\": \"" + agenciaOrigem + "\","
             + "    \"contaOrigem\": \"" + contaOrigem + "\","
             + "    \"valor\": \"" + valor + "\","
             + "    \"agenciaDestino\": \"" + agenciaDestino + "\","
             + "    \"contaDestino\": \"" + contaDestino + "\""
             + "}";
    }
    
    private String valorDepositoSaqueJson(long numeroAgencia, long numeroConta, double valor) {
        return "{"
             + "    \"numeroAgencia\": \"" + numeroAgencia + "\","
             + "    \"numeroConta\": \"" + numeroConta + "\","
             + "    \"valor\": \"" + valor + "\""
             + "}";
    }
    
    private String novaContaJson(long numeroAgencia, long codigoCliente, double saldo) {
        return "{"
             + "    \"numeroAgencia\": \"" + numeroAgencia + "\","
             + "    \"codigoCliente\": \"" + codigoCliente + "\","
             + "    \"saldo\": \"" + saldo + "\""
             + "}"; 
    }
    
    private String novoClienteJson(String cpf, String nome, String login, String senha) throws Exception {
        return "{"
             + "    \"cpf\": \"" + cpf + "\","
             + "    \"nome\": \"" + nome + "\","
             + "    \"login\": \"" + login + "\","
             + "    \"senha\": \"" + senha + "\""
             + "}";
    }

    private String novaAgenciaJson(String nome) throws Exception {
        return "{ "
             + "    \"nome\": \""+ nome + "\""
             + "}";
    }
}
