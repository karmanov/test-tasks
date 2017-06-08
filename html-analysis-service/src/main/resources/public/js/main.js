$(document).ready(function () {
    $("#url-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit();
    });

    hideElementsOnLoad();
});

function mapType(value) {
    if (1 == value) {
        return 'internal';
    } else if (2 == value) {
        return 'external';
    }
}

function hideElementsOnLoad() {
    $("#general-table").hide();
    $("#general-header").hide();
    $("#headings-header").hide();
    $("#headings-table").hide();
    $("#links-header").hide();
    $("#links-table").hide();

}

function displayElementsOnData() {
    $("#general-table").show();
    $("#general-header").show();
    $("#headings-header").show();
    $("#headings-table").show();
    $("#links-header").show();
    $("#links-table").show();
}

function getHeadingsCount(count) {
    if (count) {
        return count;
    } else {
        return 0;
    }
}

function populateGeneralTable(data) {
    $("#address").html(data.url);
    $("#url-title").html(data.title);
    $("#url-html-version").html(data.htmlVersion);
    $("#url-has-login-form").html(data.containsLoginForm.toString());
    $("#url-heading-count").html(data.headingCount);
    $("#url-internal-count").html(data.internalLinksCount);
    $("#url-external-count").html(data.externalLinksCount);
}

function populateLinksTable(data) {
    var internalLinks = data.linksMap[1];
    var externalLinks = data.linksMap[2];
    var allLinks = internalLinks.concat(externalLinks);

    $('#links-table tr').not(':first').not(':last').remove();
    var html = '';
    for (var i = 0; i < allLinks.length; i++)
        html += '<tr><td>' + allLinks[i].url +
            '</td><td>' + mapType(allLinks[i].type) + '</td>' +
            '<td>' + allLinks[i].status + '</td>' +
            '</tr>'
        ;
    $('#links-table tr').first().after(html);
}

function populateHeadingsTable(data) {
    var headingsMap = data.headingsMap;
    var h1Count = headingsMap['h1'];
    var h2Count = headingsMap['h2'];
    var h3Count = headingsMap['h3'];
    var h4Count = headingsMap['h4'];
    var h5Count = headingsMap['h5'];
    var h6Count = headingsMap['h6'];

    $("#h1-count").html(getHeadingsCount(h1Count));
    $("#h2-count").html(getHeadingsCount(h2Count));
    $("#h3-count").html(getHeadingsCount(h3Count));
    $("#h4-count").html(getHeadingsCount(h4Count));
    $("#h5-count").html(getHeadingsCount(h5Count));
    $("#h6-count").html(getHeadingsCount(h6Count));
}

function populateTables(data) {
    populateGeneralTable(data);
    populateLinksTable(data);
    populateHeadingsTable(data);
}

function fire_ajax_submit() {
    var analyze = {}
    analyze["url"] = $("#url").val();

    $("#btn-analyze").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/analyze",
        data: JSON.stringify(analyze),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            displayElementsOnData();
            populateTables(data);

            console.log("SUCCESS : ", data);
            $("#btn-analyze").prop("disabled", false);
        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("#btn-analyze").prop("disabled", false);
        }
    });

}
