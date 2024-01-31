//package com.vis.monitor.modal;
//
//
//
//import com.vis.monitor.modal.Ports;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PortsServiceImpl implements PortsService {
//
//    @Autowired
//    private PortsRepository portsRepository;
//
//    @Override
//    public Ports savePort(Ports port) {
//        return portsRepository.save(port);
//    }
//
//    @Override
//    public Ports getPortById(Long id) {
//        Optional<Ports> optionalPorts = portsRepository.findById(id);
//        return optionalPorts.orElse(null);
//    }
//
//    @Override
//    public List<Ports> getAllPorts() {
//        return portsRepository.findAll();
//    }
//
//    @Override
//    public void deletePort(Long id) {
//        portsRepository.deleteById(id);
//    }
//}
