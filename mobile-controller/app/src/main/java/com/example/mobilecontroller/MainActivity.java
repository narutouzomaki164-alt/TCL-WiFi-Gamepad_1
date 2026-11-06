package com.example.mobilecontroller;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.graphics.Color;

public class MainActivity extends Activity {

    private WebView webView;

        @Override
            protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);

                            webView = new WebView(this);
                                    webView.setBackgroundColor(Color.rgb(12, 14, 20));

                                            WebSettings settings = webView.getSettings();
                                                    settings.setJavaScriptEnabled(true);
                                                            settings.setDomStorageEnabled(true);

                                                                    webView.setWebViewClient(new WebViewClient());
                                                                            webView.loadDataWithBaseURL(
                                                                                        null, html, "text/html", "UTF-8", null
                                                                                                );

                                                                                                        setContentView(webView);
                                                                                                            }

                                                                                                                private final String html = """
                                                                                                                <!DOCTYPE html>
                                                                                                                <html>
                                                                                                                <head>
                                                                                                                <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
                                                                                                                <style>
                                                                                                                *{box-sizing:border-box}
                                                                                                                body{margin:0;background:#0c0e14;color:white;font-family:Arial;overflow:hidden;touch-action:none}
                                                                                                                .top{height:12vh;display:flex;align-items:center;justify-content:space-around;gap:8px;padding:8px}
                                                                                                                input{width:45%;background:#191e2b;color:white;border:1px solid #39465c;border-radius:8px;padding:10px}
                                                                                                                button{background:#171d2a;color:white;border:1px solid #35b9ff;border-radius:50%;font-weight:bold;touch-action:none}
                                                                                                                .connect{border-radius:8px;padding:10px}
                                                                                                                #status{font-size:12px;color:#ffbd55}
                                                                                                                .controller{height:88vh;display:grid;grid-template-columns:1fr 1fr;grid-template-rows:22% 78%;padding:5px;gap:4px}
                                                                                                                .triggers{display:flex;justify-content:space-around;align-items:center}
                                                                                                                .small{width:52px;height:42px;border-radius:10px}
                                                                                                                .center{display:flex;justify-content:center;align-items:center;gap:12px}
                                                                                                                .center button{width:65px;height:40px;border-radius:10px}
                                                                                                                .lower{display:flex;align-items:center;justify-content:space-around}
                                                                                                                .dpad{display:grid;grid-template-columns:48px 48px 48px;grid-template-rows:48px 48px 48px}
                                                                                                                .dpad button{border-radius:10px;font-size:18px}
                                                                                                                .empty{visibility:hidden}
                                                                                                                .stick{width:125px;height:125px;border:2px solid #35b9ff;border-radius:50%;display:flex;align-items:center;justify-content:center;background:#151b27}
                                                                                                                .knob{width:48px;height:48px;border-radius:50%;background:#34445b;border:2px solid #65d2ff}
                                                                                                                .abxy{display:grid;grid-template-columns:52px 52px;grid-template-rows:52px 52px;gap:10px}
                                                                                                                .abxy button{font-size:18px}
                                                                                                                </style>
                                                                                                                </head>
                                                                                                                <body>
                                                                                                                <div class="top">
                                                                                                                <input id="ip" placeholder="TV IP address" value="10.218.213.242">
                                                                                                                <button class="connect" onclick="connect()">CONNECT</button>
                                                                                                                <span id="status">Disconnected</span>
                                                                                                                </div>
                                                                                                                <div class="controller">
                                                                                                                <div class="triggers">
                                                                                                                <button class="small" data-key="LT">LT</button>
                                                                                                                <button class="small" data-key="LB">LB</button>
                                                                                                                <button class="small" data-key="LS">LS</button>
                                                                                                                </div>
                                                                                                                <div class="triggers">
                                                                                                                <button class="small" data-key="RT">RT</button>
                                                                                                                <button class="small" data-key="RB">RB</button>
                                                                                                                <button class="small" data-key="RS">RS</button>
                                                                                                                </div>
                                                                                                                <div class="lower">
                                                                                                                <div>
                                                                                                                <div class="dpad">
                                                                                                                <button class="empty"></button><button data-key="UP">▲</button><button class="empty"></button>
                                                                                                                <button data-key="LEFT">◀</button><button data-key="OK">●</button><button data-key="RIGHT">▶</button>
                                                                                                                <button class="empty"></button><button data-key="DOWN">▼</button><button class="empty"></button>
                                                                                                                </div>
                                                                                                                </div>
                                                                                                                <div class="stick" id="leftStick"><div class="knob"></div></div>
                                                                                                                </div>
                                                                                                                <div>
                                                                                                                <div class="center">
                                                                                                                <button data-key="BACK">BACK</button>
                                                                                                                <button data-key="START">START</button>
                                                                                                                </div>
                                                                                                                <div class="lower">
                                                                                                                <div class="stick" id="rightStick"><div class="knob"></div></div>
                                                                                                                <div class="abxy">
                                                                                                                <button data-key="Y">Y</button><button data-key="X">X</button>
                                                                                                                <button data-key="B">B</button><button data-key="A">A</button>
                                                                                                                </div>
                                                                                                                </div>
                                                                                                                </div>
                                                                                                                </div>
                                                                                                                <script>
                                                                                                                let ws=null;
                                                                                                                function send(k,action){
                                                                                                                 if(ws && ws.readyState===1){
                                                                                                                   ws.send(JSON.stringify({key:k,action:action}));
                                                                                                                    }
                                                                                                                    }
                                                                                                                    function connect(){
                                                                                                                     let ip=document.getElementById('ip').value.trim();
                                                                                                                      if(!ip){document.getElementById('status').innerText='Enter TV IP';return;}
                                                                                                                       try{
                                                                                                                         if(ws)ws.close();
                                                                                                                           ws=new WebSocket('ws://'+ip+':8765');
                                                                                                                             ws.onopen=()=>document.getElementById('status').innerText='Connected';
                                                                                                                               ws.onclose=()=>document.getElementById('status').innerText='Disconnected';
                                                                                                                                 ws.onerror=()=>document.getElementById('status').innerText='Connection error';
                                                                                                                                  }catch(e){document.getElementById('status').innerText='Error';}
                                                                                                                                  }
                                                                                                                                  document.querySelectorAll('[data-key]').forEach(b=>{
                                                                                                                                   b.addEventListener('pointerdown',e=>{e.preventDefault();send(b.dataset.key,'down')});
                                                                                                                                    b.addEventListener('pointerup',e=>{e.preventDefault();send(b.dataset.key,'up')});
                                                                                                                                     b.addEventListener('pointerleave',()=>send(b.dataset.key,'up'));
                                                                                                                                     });
                                                                                                                                     function setupStick(id,name){
                                                                                                                                      const stick=document.getElementById(id);
                                                                                                                                       const knob=stick.querySelector('.knob');
                                                                                                                                        let active=false;
                                                                                                                                         function move(e){
                                                                                                                                           if(!active)return;
                                                                                                                                             let r=stick.getBoundingClientRect();
                                                                                                                                               let x=e.clientX-r.left-r.width/2;
                                                                                                                                                 let y=e.clientY-r.top-r.height/2;
                                                                                                                                                   let max=35, len=Math.hypot(x,y);
                                                                                                                                                     if(len>max){x=x/len*max;y=y/len*max;}
                                                                                                                                                       knob.style.transform=`translate(${x}px,${y}px)`;
                                                                                                                                                         send(name+':'+Math.round(x/max*100)+','+Math.round(-y/max*100),'move');
                                                                                                                                                          }
                                                                                                                                                           stick.addEventListener('pointerdown',e=>{active=true;stick.setPointerCapture(e.pointerId);move(e)});
                                                                                                                                                            stick.addEventListener('pointermove',move);
                                                                                                                                                             function stop(){active=false;knob.style.transform='translate(0,0)';send(name+':0,0','move');}
                                                                                                                                                              stick.addEventListener('pointerup',stop);
                                                                                                                                                               stick.addEventListener('pointercancel',stop);
                                                                                                                                                               }
                                                                                                                                                               setupStick('leftStick','LEFT_STICK');
                                                                                                                                                               setupStick('rightStick','RIGHT_STICK');
                                                                                                                                                               </script>
                                                                                                                                                               </body>
                                                                                                                                                               </html>
                                                                                                                                                               """;
                                                                                                                                          
                                                                                                                                                               }