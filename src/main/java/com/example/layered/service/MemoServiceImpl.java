package com.example.layered.service;

import org.springframework.stereotype.Service;

/**
 * <p>Annotation @Service는 @Component와 같다, Spring Bean으로 등록한다는 뜻.</p>
 * <p>Spring Bean으로 등록되면 다른 클래스에서 주입하여 사용할 수 있다.</p>
 * <p>명시적으로 Service Layer 라는것을 나타낸다.</p>
 * <p>비지니스 로직을 수행한다.</p>
 */
@Service
public class MemoServiceImpl implements MemoService {
}
