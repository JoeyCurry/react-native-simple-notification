/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableOpacity
} from 'react-native';
import Notification from 'react-native-simple-notification'

export default class example extends Component {
  constructor(props){
      super(props);
  }

  componentDidMount(){
      Notification.init({
          title:'安卓测试通知',
          content:'安卓测试通知内容',
          smallIcon:'demo',
          largeIcon:'demo'
      })
  }

  handleClick(){
      Notification.send()
  }

  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity onPress={()=>{this.handleClick()}}>
          <Text>
            handle
          </Text>
        </TouchableOpacity>
        <Text style={styles.instructions}>
          Double tap R on your keyboard to reload,{'\n'}
          Shake or press menu button for dev menu
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('example', () => example);
