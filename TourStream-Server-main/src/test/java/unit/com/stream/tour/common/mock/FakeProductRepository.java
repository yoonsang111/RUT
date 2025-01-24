package com.stream.tour.common.mock;

import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.service.port.ProductRepository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class FakeProductRepository implements ProductRepository {
    private AtomicLong atomicLong = new AtomicLong(1L);
    private List<Product> data = new CopyOnWriteArrayList<>();


    @Override
    public Product findById(long productId) {
        return data.stream().filter(product -> product.getId() == productId).findAny().orElse(null);
    }

    @Override
    public List<Product> findByPartnerId(long partnerId) {
        return data.stream()
                .filter(product -> product.getPartner().getId() == partnerId)
                .toList();
    }

    @Override
    public List<Product> findByIdIn(List<Long> productIds) {
        return null;
    }

    @Override
    public Product create(Product product) {
        if (product.getId() == null || product.getId() == 0) {
            Product newProduct = Product.builder()
                    .id(atomicLong.getAndIncrement())
                    .partner(product.getPartner())
                    .name(product.getName())
                    .description(product.getDescription())
                    .notice(product.getNotice())
                    .content(product.getContent())
                    .tourCourse(product.getTourCourse())
                    .includedContent(product.getIncludedContent())
                    .excludedContent(product.getExcludedContent())
                    .otherContent(product.getOtherContent())
                    .minDepartureNumber(product.getMinDepartureNumber())
                    .transportation(product.getTransportation())
                    .pickupType(product.getPickupType())
                    .pickupInformation(product.getPickupInformation())
                    .pickupLocation(product.getPickupLocation())
                    .paymentPlan(product.getPaymentPlan())
                    .inquiryType(product.getInquiryType())
                    .isGuided(product.isGuided())
                    .isClosed(product.isClosed())
                    .isRepresentative(product.isRepresentative())
                    .operationStartTime(product.getOperationStartTime())
                    .operationEndTime(product.getOperationEndTime())
                    .build();
            data.add(newProduct);
            return newProduct;
        } else {
            data.add(product);
            return product;
        }
    }

    @Override
    public Product update(Product product) {
        return null;
    }
}
