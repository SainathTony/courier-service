import repository.impl.StaticCouponRepository;
import service.CostEstimationService;
import service.CouponService;
import service.InputService;
import service.OutputService;
import service.PackageManager;
import service.impl.ConsoleOutput;
import service.impl.CostEstimationServiceImpl;
import service.impl.CouponServiceImpl;
import service.impl.InputFromCommandLine;
import service.impl.PackageManagerImpl;

import java.util.Scanner;

public class CourierServiceApp {
    private final InputService inputService = new InputFromCommandLine(new Scanner(System.in));
    private final OutputService outputService = new ConsoleOutput();
    private final CouponService couponService = new CouponServiceImpl(new StaticCouponRepository());
    private final CostEstimationService costEstimationService = new CostEstimationServiceImpl(couponService);
    private final PackageManager packageManager = new PackageManagerImpl(inputService, outputService, costEstimationService);

    public static void main(String[] args) {
        new CourierServiceApp().packageManager.manage();
    }
}
