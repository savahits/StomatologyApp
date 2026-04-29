package ru.shmelev.stomatologyapp.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shmelev.stomatologyapp.domain.Client;
import ru.shmelev.stomatologyapp.dto.RequestClientCreate;
import ru.shmelev.stomatologyapp.repository.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {

    ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Client getOrCreateClient(RequestClientCreate dto) {
        Optional<Client> existingClient = clientRepository.findByPhone(dto.phone());
        if (existingClient.isPresent()) {
            Client client = existingClient.get();
            if (!client.getSurname().equals(dto.surname()) || !client.getName().equals(dto.name())) {
                throw new RuntimeException("Клиент с номером " + dto.phone() + " существует — " + client.getSurname() + " " + client.getName());
            }
            return client;
        } else {
            Client client = new Client();
            client.setName(dto.name());
            client.setSurname(dto.surname());
            client.setPatronymic(dto.patronymic());
            client.setPhone(dto.phone());
            return clientRepository.save(client);
        }
    }


    public boolean existsByPhone(String phone) {
        return clientRepository.existsByPhone(phone);
    }
}
