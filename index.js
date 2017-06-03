import {
    NativeModules,
    PushNotificationIOS,
    AlertIOS,
    Platform
} from 'react-native';

let RNMyFancyLibrary = NativeModules.RNMyFancyLibrary;
let ios = Platform.OS === 'ios';
let android = Platform.OS === 'android';
let title,content,action,soundName;


export default {
    init(options){
        let opt = {
            title:'',
            content:'',
            smallIcon:'',
            largeIcon:'',
            action:'view',
            soundName:'1007',
            ...options
        };
        if (android) {
            RNMyFancyLibrary._init(opt);
        } else {
            title = opt.title == '' ? '测试' : opt.title
            content = opt.content == '' ? '测试内容' : opt.content
            action = opt.action;
            soundName = opt.soundName;
        }
    },

    register(){
        if (ios) {
            PushNotificationIOS.addEventListener('localNotification', this._onLocalNotification);
        }
    },
    send(){
        if (android) {
            RNMyFancyLibrary.sendNotification()
        } else {
            PushNotificationIOS.presentLocalNotification({
                alertBody:content,
                alertAction:action,
                userInfo:{'name':'jiangjun'},
                soundName:soundName,
                applicationIconBadgeNumber:1
            })
        }
    },

    _onLocalNotification(notification){
        AlertIOS.alert(
        title,
        notification.getMessage(),
        [{
            text: '确定',
            onPress: null,
        }]
        );
    }   
}
