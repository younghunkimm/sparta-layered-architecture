package com.example.layered.service;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.repository.MemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Annotation @Service는 @Component와 같다, Spring Bean으로 등록한다는 뜻.</p>
 * <p>Spring Bean으로 등록되면 다른 클래스에서 주입하여 사용할 수 있다.</p>
 * <p>명시적으로 Service Layer 라는것을 나타낸다.</p>
 * <p>비지니스 로직을 수행한다.</p>
 */
@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    public MemoServiceImpl(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public MemoResponseDto saveMemo(MemoRequestDto dto) {

        // 요청받은 데이터로 Memo 객체 생성 ID 없음
        Memo memo = new Memo(dto.getTitle(), dto.getContents());

        // DB 저장
        Memo savedMemo = memoRepository.saveMemo(memo);

        return new MemoResponseDto(savedMemo);
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {

        return memoRepository.findAllMemos();
    }

}
