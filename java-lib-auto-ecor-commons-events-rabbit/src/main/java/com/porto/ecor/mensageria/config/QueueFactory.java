package com.porto.ecor.mensageria.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueFactory {

    /**
     * Cria uma fila com os parâmetros fornecidos, incluindo propriedades padrão.
     *
     * @param name      Nome da fila.
     * @param arguments Argumentos adicionais para a fila.
     * @return A fila criada.
     */
    public Queue createQueue(String name, Map<String, Object> arguments) {
        Map<String, Object> defaultArguments = new HashMap<>();
        defaultArguments.put("x-message-ttl", 864000); // Tempo de vida das mensagens em milissegundos
        defaultArguments.put("x-max-length-bytes", 1024000); // Tamanho máximo total das mensagens na fila em bytes
        defaultArguments.put("x-overflow", "reject-publish"); // Ação a ser tomada quando a fila atinge seu limite máximo de mensagens
        defaultArguments.put("x-expires", 60000000); // Tempo em milissegundos para que a fila seja excluída automaticamente quando não estiver em uso
        defaultArguments.put("x-max-priority", 10); // Número máximo de prioridades para a fila
        defaultArguments.put("x-queue-mode", "lazy"); // Modo da fila (e.g., "lazy" para armazenar mensagens em disco)

        if (arguments != null) {
            defaultArguments.putAll(arguments);
        }

        return new Queue(name, true, false, false, defaultArguments);
    }
    
    /**
     * Cria uma fila DLQ com os parâmetros fornecidos.
     *
     * @param name Nome da fila DLQ.
     * @return A fila DLQ criada.
     */
    public Queue createDLQ(String name) {
    	Map<String, Object> defaultArguments = new HashMap<>();
    	defaultArguments.put("x-max-priority", 11); // Número máximo de prioridades para a fila
        return new Queue(name, true, false, false, defaultArguments);
    }
    
}
