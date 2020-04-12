package com.marquinhus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class MeuResource {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@RequestBody Cliente cliente) {
        // service.save(client)
        return cliente;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        // service.buscarPorId(client)
        // service.delete(client)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        // service.buscarPorId(client)
        // service.update(client)
        return cliente;
    }

    @GetMapping("{id}")
    public Cliente obterDadosCliente(@PathVariable Long id) {
        Cliente cliente = new Cliente("Fulano", "000.000.000-00");
        return cliente;
    }
}
