package net.nikolaj_fedulov.taco_cloud.repositories;

import net.nikolaj_fedulov.taco_cloud.models.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}
