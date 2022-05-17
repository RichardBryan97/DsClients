package com.DsClients.ClientesBase.repositories;

import com.DsClients.ClientesBase.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
