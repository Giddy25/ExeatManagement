    function openPopup(event) {
        event.preventDefault();
        var url = event.target.href;
        var width = 650;
        var height = 650;
        var left = (screen.width / 2) - (width / 2);
        var top = (screen.height / 2) - (height / 2);
        var popup = window.open(url, '_blank', 'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=' + width + ', height=' + height + ', top=' + top + ', left=' + left);
        popup.focus();
        return false;
    }
