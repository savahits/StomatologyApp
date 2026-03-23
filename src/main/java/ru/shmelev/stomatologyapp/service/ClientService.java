package ru.shmelev.stomatologyapp.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shmelev.stomatologyapp.domain.Client;
import ru.shmelev.stomatologyapp.dto.RequestClientCreate;
import ru.shmelev.stomatologyapp.repository.ClientRepository;

@Service
public class ClientService {

    ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Client getOrCreate(RequestClientCreate dto) {
        return clientRepository.findByPhone(dto.getPhone())
                .orElseGet(() -> {
                    Client client = new Client();
                    client.setName(dto.getName());
                    client.setSurname(dto.getSurname());
                    client.setPatronymic(dto.getPatronymic());
                    client.setPhone(dto.getPhone());
                    return clientRepository.save(client);
                });
    }
}
