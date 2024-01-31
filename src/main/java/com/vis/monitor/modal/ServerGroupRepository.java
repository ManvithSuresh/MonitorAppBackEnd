package com.vis.monitor.modal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ServerGroupRepository extends JpaRepository<ServerGroup, Long> {

}
