CKEDITOR.plugins.add('indentfirstline', {
        lang: 'ru',
        init: function(editor) {

            /*editor.on('key', function(evt) {
                if (evt.data.keyCode === 13) {
                    setTimeout(function () {
                        var p = editor.elementPath().contains('p');
                        if(p.getStyle('text-indent')) {
                            p.removeStyle('text-indent');
                        }
                    }, 10);
                }
            });*/
            editor.ui.addRichCombo( 'IndentFirstLine', {
                label: editor.lang.indentfirstline.indentFirstLine,
                title: editor.lang.indentfirstline.indentFirstLine,
                toolbar: 'styles, 2',

                panel: {
                    css: [ CKEDITOR.skin.getPath( 'editor' ) ].concat( editor.config.contentsCss ),
                    multiSelect: false,
                    attributes: { 'aria-label': editor.lang.indentfirstline.indentFirstLine }
                },

                init: function() {
                    var me = this;
                    this._.list.onClick = function( value, marked ) {

                        if ( me.onClick )
                            me.onClick.call( me, value, marked );
                        if(value != 'custom')
                            me._.panel.hide();
                    };
                    this._.list.mark = function( value ) {
                        if ( !this.multiSelect )
                            this.unmarkAll();

                        var itemId = this._.items[ value];
                        if(!itemId) {
                            this.element.findOne('input').setValue(value);
                            me.setValue( '', editor.lang.indentfirstline.indentFirstLin );
                            return;
                        } else {
                            this.element.findOne('input').setValue('');
                        }
                        var item = this.element.getDocument().getById( itemId );
                        item.addClass( 'cke_selected' );

                        this.element.getDocument().getById( itemId + '_option' ).setAttribute( 'aria-selected', true );
                        this.onMark && this.onMark( item );
                    };

                    this.startGroup( editor.lang.indentfirstline.indentFirstLine );

                    this.add( 'custom', '<input type="text" id="customindedtfirstlinevalue" style="width: calc(100% - 3px);">', 'custom' );
                    var names = ['0cm', '0.25cm', '0.5cm', '0.75', '1cm', '1.25cm', '1.5cm', '1.75cm', '2cm', '2.25cm', '2.5cm', '2.75cm', '3cm'];
                    for ( var i = 0; i < names.length; i++ ) {
                        var name = names[ i ];
                        // Add the tag entry to the panel list.
                        this.add( name, name , name );
                    }
                },
                apply: function(unit) {
                    editor.focus();
                    editor.fire( 'saveSnapshot' );
                    var range = editor.getSelection().getRanges()[0];
                    var iterator = range.createIterator(), p;
                    while ( ( p = iterator.getNextParagraph('p') ) ) {
                       /* var indent = p.getStyle('text-indent');
                        if(indent) {
                            p.removeStyle('text-indent')
                        } else*/
                            p.setStyle('text-indent', isNaN(unit) ? unit : unit + 'cm')
                    }

                    editor.fire( 'saveSnapshot' );
                },
                onClick: function( value ) {
                    var input = this._.list.element.findOne('input');
                    var self = this;
                    if(value == 'custom') {
                        input.once('blur', function(e) {
                            self.apply(e.sender.getValue());
                        });
                        return;
                    } else {
                        input.setValue("");
                    }
                    this.apply(value);
                },

                onRender: function() {
                    var self = this;
                    editor.on( 'selectionChange', function( ev ) {
                        var currentValue = this.getValue();
                        var me = self;
                        var elementPath = ev.data.path,
                            elements = elementPath.elements;

                        // For each element into the elements path.
                        var state = CKEDITOR.TRISTATE_DISABLED;
                        var identValue;
                        for ( var i = 0, element; i < elements.length; i++ ) {
                            element = elements[ i ];
                            if(element.$.tagName == 'P') {
                                state = CKEDITOR.TRISTATE_OFF;
                                if(element.getStyle('text-indent')) {
                                    state = CKEDITOR.TRISTATE_ON;
                                    identValue = element.getStyle('text-indent');
                                    //me.add( identValue, identValue , identValue );
                                } else {
                                    if(me._.list)
                                        me._.list.element.findOne('input').setValue('');
                                }
                                break;
                            }
                        }

                        this.setState( state );

                        this.setValue( identValue, editor.lang.indentfirstline.indentFirstLin );
                    }, this );
                }
            });


        }
});
