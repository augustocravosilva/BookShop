package warehouseinterface;


import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface IWarehouseService extends Serializable {
    public List<IBookOrder> getAllOrders();
    public IBookOrder getOrder(int id);
    public void saveOrder(IBookOrder order);
}
