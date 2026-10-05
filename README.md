<h1 align="center">TODO</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=28"><img alt="API" src="https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">  
📱 TODO App은 MVVM 아키텍처를 기반으로 Hilt, Coroutine, Flow, Jetpack(Compose, Room, ViewModel) 및 Material Design3를 활용하여 개발하였습니다.
</p>

## Tech stack & Open-source libraries
- Minimum SDK level 28
- [Kotlin](https://kotlinlang.org/) 기반으로, 비동기 작업을 위해 [Coroutine](https://github.com/Kotlin/kotlinx.coroutines), [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)를 활용하였습니다.
- Jetpack Libraries
  - [Compose](https://developer.android.com/compose) : 선언형 UI 개발을 위한 Android toolkit
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) : 안드로이드 생명 주기를 관찰하고 생명 주기 변화에 따라 UI 상태 관리
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) : UI 관련 데이터를 관리하며 생명 주기를 인식하여, 설정 변경 시에도 데이터가 유지되도록 보장
  - [Navigation](https://developer.android.com/guide/navigation) : 스크린 간의 이동을 용이하게 하며, 의존성 주입을 위해 [Hilt Navigation Compose](https://developer.android.com/reference/kotlin/androidx/hilt/navigation/compose/package-summary)를 활용하여 보완
  - [Room](https://developer.android.com/training/data-storage/room) : 원활한 데이터베이스 접근을 위해 SQLite 추상화 계층을 사용하여 데이터베이스를 구성
- Architecture
  - MVVM Architecture (View - ViewModel - Model) : 관심사 분리를 돕고 유지보수성 향상
  - Repository Pattern : 다양한 데이터 소스와 애플리케이션의 비즈니스 로직 사이에서 중재자 역할
- Kotlin Serialization : 리플렉션(reflection)을 사용하지 않고 컴파일 타임에 직렬화 코드를 생성하므로, 성능 향상
- ksp : 코드 생성 및 분석을 위한 API

## Architecture
MVVM 아키텍처를 준수하고, [Google의 공식 아키텍처 지침](https://developer.android.com/topic/architecture?hl=ko)에 맞춰 Repository 패턴을 구현하였습니다.

### Layer
Presentation/Domain/Data Layered Architecture 형태로 설계하였습니다.
<img width="500" src="https://github.com/user-attachments/assets/ad71bc5d-fa5f-4942-bb68-1594e71bcf62">

### UI Layer
상태는 아래로 보내고, 이벤트는 위로 올리는 단방향 데이터 흐름(UDF)으로 구성하였습니다.
<img width="400" src="https://github.com/user-attachments/assets/5b6b1b67-29a7-4f52-b9c9-1eaff7d915c5">

## Module
Multi-module 구조이며 각 Feature마다 모듈 형태로 구성하였습니다.
![graphviz](https://github.com/user-attachments/assets/86bc6b29-e962-4b84-bb85-0bfb9ef90ec1)
