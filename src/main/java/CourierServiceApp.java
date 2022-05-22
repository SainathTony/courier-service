import repository.impl.StaticCouponRepository;
import service.CostEstimationService;
import service.CouponService;
import service.DeliveryService;
import service.InputService;
import service.OutputService;
import service.CourierManager;
import service.impl.ConsoleOutput;
import service.impl.CostEstimationServiceImpl;
import service.impl.CouponServiceImpl;
import service.impl.CourierInputCMD;
import service.impl.CourierManagerImpl;
import service.impl.DeliveryServiceImpl;
import service.impl.VehicleInputCMD;
import service.impl.VehicleManager;

import java.util.Scanner;

public class CourierServiceApp {
    private final InputService courierInputService = new CourierInputCMD(new Scanner(System.in));
    private final InputService vehicleInputService = new VehicleInputCMD(new Scanner(System.in));
    private final OutputService outputService = new ConsoleOutput();
    private final CouponService couponService = new CouponServiceImpl(new StaticCouponRepository());
    private final CostEstimationService costEstimationService = new CostEstimationServiceImpl(couponService);
    private final VehicleManager vehicleManager = new VehicleManager();
    private final DeliveryService deliveryService = new DeliveryServiceImpl(vehicleManager);
    private final CourierManager courierManager = new CourierManagerImpl(courierInputService, vehicleInputService, outputService, costEstimationService, deliveryService);

    public void start() {
        courierManager.manage();
    }

    public static void main(String[] args) {
        new CourierServiceApp().start();
    }
}
