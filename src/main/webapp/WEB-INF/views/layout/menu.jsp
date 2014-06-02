 <ul id="sddm">
    <li><a href="home.go">Home</a></li>
    <li><a href="#" 
        onmouseover="mopen('m1')" 
        onmouseout="mclosetime()">Create</a>
        <div id="m1" 
            onmouseover="mcancelclosetime()" 
            onmouseout="mclosetime()">
        <a href="createruleform.go">Create rule</a>
        <a href="createstrategyform.go">Create strategy</a>
        </div>
    </li>
    <li><a href="#" 
        onmouseover="mopen('m2')" 
        onmouseout="mclosetime()">List</a>
        <div id="m2" 
            onmouseover="mcancelclosetime()" 
            onmouseout="mclosetime()">
        <a href="list.go?listType=STRATEGY">List strategies</a>
        <a href="list.go?listType=RULE">List rules</a>
        <a href="list.go?listType=DATASET">List datasets</a>
        <a href="list.go?listType=INDICATOR">List indicators</a>
        </div>
    </li>
    <li><a href="#" 
        onmouseover="mopen('m3')" 
        onmouseout="mclosetime()">Upload</a>
        <div id="m3" 
            onmouseover="mcancelclosetime()" 
            onmouseout="mclosetime()">
        <a href="datasetupload.go">Upload dataset</a>
        <a href="strategyupload.go">Upload strategy</a>
        </div>
    </li>
    <li><a href="#" 
        onmouseover="mopen('m4')" 
        onmouseout="mclosetime()">Analysis</a>
        <div id="m4" 
            onmouseover="mcancelclosetime()" 
            onmouseout="mclosetime()">
        <a href="configurerun.go">Run analysis</a>
        <a href="visualise.go">Results visualisation</a>
        </div>
    </li>
    <li><a href="#" 
        onmouseover="mopen('m5')" 
        onmouseout="mclosetime()">Delete</a>
        <div id="m5" 
            onmouseover="mcancelclosetime()" 
            onmouseout="mclosetime()">
        <a href="deletestrategyform.go">Delete strategy</a>
        <a href="deletedatasetform.go">Delete dataset</a>
        <a href="deleteresultsetform.go">Delete result set</a>
        <a href="deleteruleform.go">Delete rule</a>
        </div>
    </li>
    <li><a href="help.go">Help</a></li>
    <li><a href="contact.go">Contact</a></li>
</ul>
<div style="clear:both"></div>