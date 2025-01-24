## 소개

- 목적
  - TourStream 프로젝트의 다양한 웹페이지 제공을 위한 프로젝트 입니다.
-  주요기능
	- TourStream 소개
	- 도입 문의
	- 링크 예약 결제 폼
	- 업체 제공 예약 플랫폼
- 기술 스택
  - NextJS
  - React
  - Typescript
  - Framer-motion
  - React-query
  - Zustand
  - Tailwindcss
 
## 프로젝트 구조

```
src
├── app
│   ├── (home)              # 홈페이지
│   │   ├── _components     # (home)로컬 컴포넌트
│   │   └── page.tsx
│   ├── favicon.ico
│   ├── globals.css
│   ├── inquiry             # 도입문의 페이지
│   └── layout.tsx          # 전체 레이아웃
├── components              # 공용 컴포넌트
├── lib                     # 공용 라이브러리
└── stores                  # 공용 전역 상태
```
