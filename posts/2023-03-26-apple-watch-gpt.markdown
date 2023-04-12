---
title: Apple Watch + ChatGPT
author: Kenny Cason
tags: ios, chatgpt, apple watch
---

I recently purchased an Apple Watch, with two primary goals:
1. Track my health, specifically sleep and exercise.
2. Build apps.


Having never built anything for the Apple Watch, I was pretty excited to get started and had a ton of ideas. Additionally, it has been almost 10 years since I last programmed in Swift to build a <a href="/posts/2014-10-26-swift-boxxle.html" target="blank">Boxxle Clone</a>. 

The code can be found on GitHub, <a href="https://github.com/kennycason/watchgpt" target="blank">here</a>.

## Features

### Supported Versions

The app currently supports GPT-3, GPT-3.5, and GPT-4 via the `/completions` and `/chat/completions` endpoints.

<img class="margin5" src="/images/watchgpt/chatgpt3.5.png" width="250"/>

### Voice-To-Text

Using the standard keyboard we can select the microphone to initiate voice to text. We can use this to more easily interact with ChatGPT. 

<img class="margin5" src="/images/watchgpt/keyboard.png" width="250"/><img class="margin5" src="/images/watchgpt/voice_to_text.png" width="250"/>

### Text-To-Voice

I really wanted to make the app simple enough that even children who can not read are able to use the app. To do this, I decided to explore what it would take to implement Voice-To-Text on iOS/Watch. 

Much to my surprise, it was incredibly easy to implement! The process can be broken into three parts.

1. The first step was use iOS's `NaturalLanguage` library to detect the language.

```swift
import Foundation
import NaturalLanguage

class LanguageDetector {
    func detect(text: String) -> String {
        let languageRecognizer = NLLanguageRecognizer()
        languageRecognizer.processString(text)
        if let dominantLanguage = languageRecognizer.dominantLanguage {
            return dominantLanguage.rawValue
        }
        return "en-US"
    }
}
```

2. The second step is to use iOS's `AVSpeechSynthesizer` to perform the actual Voice-To-Text feature.

```swift
import Foundation
import AVFoundation

class TextToSpeech {
    private let synthesizer: AVSpeechSynthesizer = AVSpeechSynthesizer()
    
    init() {
        do {
            try AVAudioSession.sharedInstance().setCategory(AVAudioSession.Category.playback)
            try AVAudioSession.sharedInstance().setActive(true)
         }
        catch {
            print("Fail to enable AVAudioSession")
        }
    }
    
    func stopSpeaking() {
        if (isSpeaking()) {
            synthesizer.stopSpeaking(at: .immediate)
        }
    }

    func isSpeaking() -> Bool {
        return synthesizer.isSpeaking
    }
    
    func speak(text: String) {
        let utterance = AVSpeechUtterance(string: text)
        utterance.volume = 1.0

        let languageDetector = LanguageDetector()
        let language = languageDetector.detect(text: text)
        let voice = AVSpeechSynthesisVoice(language: language)
        utterance.voice = voice
        
        synthesizer.speak(utterance)
    }
}
```

3. The final step was to add a "🔊" button to trigger the Text-To-Voice feature.
 
<img class="margin5" src="/images/watchgpt/text_to_voice.png" width="250"/>

### Settings + Other Models

The app supports multiple settings such as: 
- Set response Max Tokens
- Toggle models used in Chat Completions API (GPT-3.5 + GPT-4)
- Toggle models used in Completions API (GPT-3.0)
- Set API Key
- Toggle whether to pass historical messages along as context. (Required if you want to maintain a conversation with ChatGPT)
- Clear History

<img class="margin5" src="/images/watchgpt/settings.png" width="250"/>


### Questions - Misc

<img class="margin5" src="/images/watchgpt/question_pokemon.png" width="250"/><img class="margin5" src="/images/watchgpt/question_chinese_singer.png" width="250"/><img class="margin5" src="/images/watchgpt/question_wa_governor.png" width="250"/>

### Questions - Top World Cities by Population

<img class="margin5" src="/images/watchgpt/question_world_cities01.png" width="250"/><img class="margin5" src="/images/watchgpt/question_world_cities02.png" width="250"/><img class="margin5" src="/images/watchgpt/question_world_cities03.png" width="250"/>


### Questions - Children's Short Story about a Turtle in Chinese

<img class="margin5" src="/images/watchgpt/turtle_story01.png" width="250"/><img class="margin5" src="/images/watchgpt/turtle_story02.png" width="250"/><img class="margin5" src="/images/watchgpt/turtle_story03.png" width="250"/><img class="margin5" src="/images/watchgpt/turtle_story04.png" width="250"/>